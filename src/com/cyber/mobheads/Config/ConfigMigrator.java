package com.cyber.mobheads.Config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Brings a server's existing config.yml up to date with the config bundled in the jar,
 * without touching values the server owner chose.
 *
 * Runs only when the file's config_version is lower than the bundled one. It:
 *  - backs up the old file (config.yml.backup-v<old version>)
 *  - moves head entries that sit under the wrong list (e.g. old Copper Golems under ListOfFish)
 *  - adds anything missing (new mobs, new settings), with their comments
 *  - adds missing advancement links to existing heads (keeps any extra links the owner added)
 *  - replaces malformed or duplicate random_uuid values
 */
public final class ConfigMigrator {

	public static final String VERSION_KEY = "config_version";
	private static final String[] ROOTS = {"ListOfMobs", "ListOfFish"};
	private static final Pattern UUID_PATTERN =
			Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

	private ConfigMigrator() {}

	public static final class Report {
		public boolean migrated;
		public int fromVersion;
		public int toVersion;
		public int movedEntries;
		public int addedHeads;
		public int addedValues;
		public int mergedAdvancementLists;
		public int fixedUuids;
		public File backup;

		public String summary() {
			return "Config updated from v" + fromVersion + " to v" + toVersion + ": "
					+ addedHeads + " head(s) added, "
					+ addedValues + " other setting(s) added, "
					+ mergedAdvancementLists + " advancement list(s) updated, "
					+ movedEntries + " misplaced head(s) moved, "
					+ fixedUuids + " UUID(s) fixed."
					+ (backup != null ? " Backup saved as " + backup.getName() : "");
		}
	}

	/**
	 * Migrates the config file in place if it is older than the bundled defaults.
	 */
	public static Report migrateFile(File configFile, YamlConfiguration defaults)
			throws IOException, InvalidConfigurationException {
		Report report = new Report();
		report.toVersion = defaults.getInt(VERSION_KEY, 0);

		YamlConfiguration user = new YamlConfiguration();
		user.options().width(4096); // keep long lines (texture codes) on one line
		user.load(configFile);
		report.fromVersion = user.getInt(VERSION_KEY, 0);

		if (report.fromVersion >= report.toVersion) {
			return report;
		}

		File backup = new File(configFile.getParentFile(), "config.yml.backup-v" + report.fromVersion);
		if (backup.exists()) {
			backup = new File(configFile.getParentFile(),
					"config.yml.backup-v" + report.fromVersion + "-" + System.currentTimeMillis());
		}
		Files.copy(configFile.toPath(), backup.toPath());
		report.backup = backup;

		migrate(user, defaults, report);
		user.save(configFile);
		report.migrated = true;
		return report;
	}

	/** Applies all migration steps to an in-memory config. */
	public static void migrate(YamlConfiguration user, YamlConfiguration defaults, Report report) {
		moveMisplacedEntries(user, defaults, report);
		addMissingValues(user, defaults, report);
		mergeAdvancementLists(user, defaults, report);
		fixUuids(user, defaults, report);
		normalizeVanillaMarkers(user);

		user.set(VERSION_KEY, report.toVersion);
		copyComments(user, defaults, VERSION_KEY);
	}

	/** Moves heads that are under the wrong root list (e.g. ListOfFish instead of ListOfMobs). */
	private static void moveMisplacedEntries(YamlConfiguration user, YamlConfiguration defaults, Report report) {
		for (String root : ROOTS) {
			ConfigurationSection section = user.getConfigurationSection(root);
			if (section == null) continue;

			for (String key : new ArrayList<>(section.getKeys(false))) {
				if (defaults.contains(root + "." + key)) continue;

				for (String otherRoot : ROOTS) {
					if (otherRoot.equals(root)) continue;
					String correctPath = otherRoot + "." + key;
					if (!defaults.isConfigurationSection(correctPath)) continue;

					ConfigurationSection misplaced = section.getConfigurationSection(key);
					if (misplaced != null && !user.contains(correctPath)) {
						user.createSection(correctPath, toMap(misplaced));
					}
					user.set(root + "." + key, null);
					report.movedEntries++;
					break;
				}
			}
		}
	}

	/** Adds every value the bundled config has that the server's config is missing. */
	private static void addMissingValues(YamlConfiguration user, YamlConfiguration defaults, Report report) {
		Set<String> newHeads = new HashSet<>();
		for (String root : ROOTS) {
			ConfigurationSection defaultRoot = defaults.getConfigurationSection(root);
			if (defaultRoot == null) continue;
			for (String key : defaultRoot.getKeys(false)) {
				if (!user.contains(root + "." + key)) newHeads.add(root + "." + key);
			}
		}
		report.addedHeads = newHeads.size();

		for (String path : defaults.getKeys(true)) {
			if (path.equals(VERSION_KEY) || defaults.isConfigurationSection(path) || user.contains(path)) {
				continue;
			}
			if (blockedByNonSectionParent(user, path)) {
				continue; // the owner replaced a section with a plain value; leave it alone
			}

			List<String> createdPaths = new ArrayList<>();
			for (String prefix : prefixes(path)) {
				if (!user.contains(prefix)) createdPaths.add(prefix);
			}

			user.set(path, defaults.get(path));
			for (String created : createdPaths) {
				copyComments(user, defaults, created);
			}

			if (!isInside(path, newHeads)) {
				report.addedValues++;
			}
		}
	}

	/** Adds advancement links from the bundled config that an existing head is missing. */
	private static void mergeAdvancementLists(YamlConfiguration user, YamlConfiguration defaults, Report report) {
		for (String path : defaults.getKeys(true)) {
			if (!path.endsWith(".advancements") || !defaults.isList(path) || !user.isList(path)) continue;

			List<String> merged = new ArrayList<>(user.getStringList(path));
			boolean changed = false;
			for (String link : defaults.getStringList(path)) {
				if (!merged.contains(link)) {
					merged.add(link);
					changed = true;
				}
			}
			if (changed) {
				user.set(path, merged);
				report.mergedAdvancementLists++;
			}
		}
	}

	/** Replaces malformed or duplicate random_uuid values, keeping the first use of each UUID. */
	private static void fixUuids(YamlConfiguration user, YamlConfiguration defaults, Report report) {
		Set<String> seen = new HashSet<>();
		for (String path : new ArrayList<>(user.getKeys(true))) {
			if (!path.endsWith(".random_uuid")) continue;
			Object value = user.get(path);
			if (!(value instanceof String)) continue; // [vanilla] is parsed as a list; leave it

			String uuid = ((String) value).trim();
			if (uuid.equalsIgnoreCase("[vanilla]")) continue;
			if (UUID_PATTERN.matcher(uuid).matches() && seen.add(uuid.toLowerCase(Locale.ROOT))) continue;

			String replacement = defaults.getString(path);
			if (replacement == null || !UUID_PATTERN.matcher(replacement).matches()
					|| seen.contains(replacement.toLowerCase(Locale.ROOT))) {
				replacement = UUID.randomUUID().toString();
			}
			user.set(path, replacement);
			seen.add(replacement.toLowerCase(Locale.ROOT));
			report.fixedUuids++;
		}
	}

	/**
	 * Unquoted [vanilla] in YAML is a one-item list, which would be saved back as "- vanilla".
	 * Store it as the plain text "[vanilla]" instead (reads the same, saves as '[vanilla]').
	 */
	private static void normalizeVanillaMarkers(YamlConfiguration user) {
		for (String path : new ArrayList<>(user.getKeys(true))) {
			Object value = user.get(path);
			if (value instanceof List<?> list && list.size() == 1 && "vanilla".equals(String.valueOf(list.get(0)))) {
				user.set(path, "[vanilla]");
			}
		}
	}

	// ---- helpers ----

	private static void copyComments(YamlConfiguration user, YamlConfiguration defaults, String path) {
		List<String> comments = defaults.getComments(path);
		if (!comments.isEmpty() && user.getComments(path).isEmpty()) {
			user.setComments(path, comments);
		}
	}

	private static boolean blockedByNonSectionParent(YamlConfiguration user, String path) {
		for (String prefix : prefixes(path)) {
			if (prefix.equals(path)) break;
			if (user.contains(prefix) && !user.isConfigurationSection(prefix)) return true;
		}
		return false;
	}

	/** "a.b.c" -> ["a", "a.b", "a.b.c"] */
	private static List<String> prefixes(String path) {
		List<String> result = new ArrayList<>();
		int index = path.indexOf('.');
		while (index != -1) {
			result.add(path.substring(0, index));
			index = path.indexOf('.', index + 1);
		}
		result.add(path);
		return result;
	}

	private static boolean isInside(String path, Set<String> sections) {
		for (String section : sections) {
			if (path.equals(section) || path.startsWith(section + ".")) return true;
		}
		return false;
	}

	private static Map<String, Object> toMap(ConfigurationSection section) {
		Map<String, Object> map = new LinkedHashMap<>();
		for (String key : new LinkedHashSet<>(section.getKeys(false))) {
			Object value = section.get(key);
			map.put(key, value instanceof ConfigurationSection ? toMap((ConfigurationSection) value) : value);
		}
		return map;
	}
}
