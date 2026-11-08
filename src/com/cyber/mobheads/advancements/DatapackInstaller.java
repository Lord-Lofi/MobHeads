package com.cyber.mobheads.advancements;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.stream.Stream;

/**
 * Installs the advancements data pack bundled in the plugin jar into the main
 * world's datapacks folder, and keeps it in sync with the jar.
 *
 * A fingerprint of the bundled files (names + CRCs) is written into the pack.
 * On startup the installed fingerprint is compared with the jar's; the pack is
 * only rewritten when they differ, so normal restarts do no extra work.
 */
public final class DatapackInstaller {

	private static final String JAR_PREFIX = "achievements/";
	private static final String PACK_FOLDER = "mobheads";
	private static final String MARKER_FILE = ".mobheads-pack-version";

	public enum Result { INSTALLED, UPDATED, UP_TO_DATE, FAILED }

	private DatapackInstaller() {}

	/**
	 * @param plugin  the plugin
	 * @param jarFile the plugin's jar (JavaPlugin#getFile())
	 * @param force   rewrite the pack even if it is already up to date
	 */
	public static Result install(Plugin plugin, File jarFile, boolean force) {
		File packDir = getPackDirectory();
		if (packDir == null) {
			plugin.getLogger().warning("Could not find the main world folder; advancements data pack not installed.");
			return Result.FAILED;
		}

		try (JarFile jar = new JarFile(jarFile)) {
			List<JarEntry> entries = new ArrayList<>();
			Enumeration<JarEntry> all = jar.entries();
			while (all.hasMoreElements()) {
				JarEntry entry = all.nextElement();
				if (entry.getName().startsWith(JAR_PREFIX) && !entry.isDirectory()
						&& entry.getName().length() > JAR_PREFIX.length()) {
					entries.add(entry);
				}
			}
			if (entries.isEmpty()) {
				plugin.getLogger().warning("No advancements data pack found inside the plugin jar.");
				return Result.FAILED;
			}
			entries.sort(Comparator.comparing(JarEntry::getName));

			String fingerprint = fingerprint(entries);
			Path marker = packDir.toPath().resolve(MARKER_FILE);
			boolean existed = packDir.exists();

			if (!force && Files.isRegularFile(marker)
					&& fingerprint.equals(Files.readString(marker, StandardCharsets.UTF_8).trim())) {
				return Result.UP_TO_DATE;
			}

			// Remove the old pack completely so renamed/removed advancements don't linger.
			if (existed) {
				deleteRecursively(packDir.toPath());
			}

			Path root = packDir.toPath().normalize();
			for (JarEntry entry : entries) {
				Path target = root.resolve(entry.getName().substring(JAR_PREFIX.length())).normalize();
				if (!target.startsWith(root)) {
					continue; // never write outside the pack folder
				}
				Files.createDirectories(target.getParent());
				try (InputStream in = jar.getInputStream(entry)) {
					Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
				}
			}
			Files.writeString(marker, fingerprint, StandardCharsets.UTF_8);

			return existed ? Result.UPDATED : Result.INSTALLED;
		} catch (IOException | RuntimeException e) {
			plugin.getLogger().log(Level.WARNING, "Failed to install the advancements data pack", e);
			return Result.FAILED;
		}
	}

	/** Folder the pack is installed into: <main world>/datapacks/mobheads */
	public static File getPackDirectory() {
		List<World> worlds = Bukkit.getWorlds();
		if (worlds.isEmpty()) {
			return null;
		}
		World main = worlds.get(0);
		File worldDir = new File(Bukkit.getWorldContainer(), main.getName());
		if (!new File(worldDir, "level.dat").isFile()) {
			worldDir = main.getWorldFolder();
		}
		return new File(new File(worldDir, "datapacks"), PACK_FOLDER);
	}

	private static String fingerprint(List<JarEntry> entries) {
		StringBuilder sb = new StringBuilder();
		for (JarEntry entry : entries) {
			sb.append(entry.getName()).append(':').append(Long.toHexString(entry.getCrc())).append(';');
		}
		return Integer.toHexString(sb.toString().hashCode()) + "-" + entries.size();
	}

	private static void deleteRecursively(Path dir) throws IOException {
		try (Stream<Path> walk = Files.walk(dir)) {
			for (Path p : (Iterable<Path>) walk.sorted(Comparator.reverseOrder())::iterator) {
				Files.deleteIfExists(p);
			}
		}
	}
}
