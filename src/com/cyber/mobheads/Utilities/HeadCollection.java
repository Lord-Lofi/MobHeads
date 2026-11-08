package com.cyber.mobheads.Utilities;

import com.cyber.mobheads.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Tracks which mob heads each player has collected and builds the /mobheads collection book.
 *
 * A head counts per mob (or mob variant), i.e. per entry directly under ListOfMobs / ListOfFish,
 * no matter which of its textures dropped. Collected heads are stored on the player. Heads
 * collected before this was tracked are picked up from the player's advancement progress.
 */
public final class HeadCollection {

	/** Old config entries that no mob can drop any more. */
	private static final Set<String> LEGACY_ENTRIES = Set.of("black_cat", "ginger_cat", "siamese_cat");
	private static final String[] ROOTS = {"ListOfMobs", "ListOfFish"};
	private static final int LINES_PER_PAGE = 13;
	private static final int CHARS_PER_LINE = 19;

	private HeadCollection() {}

	private static NamespacedKey key() {
		return new NamespacedKey(Main.getPlugin(), "collected_heads");
	}

	/** Records that the player got a head of this mob. */
	public static void record(Player player, MobNames mob) {
		if (player == null || mob == null) return;
		Set<String> stored = getStored(player);
		if (stored.add(mob.name().toLowerCase(Locale.ROOT))) {
			player.getPersistentDataContainer().set(key(), PersistentDataType.STRING, String.join(",", stored));
		}
	}

	private static Set<String> getStored(Player player) {
		PersistentDataContainer data = player.getPersistentDataContainer();
		String value = data.get(key(), PersistentDataType.STRING);
		Set<String> result = new LinkedHashSet<>();
		if (value != null && !value.isEmpty()) {
			result.addAll(Arrays.asList(value.split(",")));
		}
		return result;
	}

	/** All collectable heads: config entry name (lowercase) -> config entry path. */
	private static Map<String, String> allHeads(FileConfiguration config) {
		Map<String, String> heads = new TreeMap<>();
		for (String root : ROOTS) {
			ConfigurationSection section = config.getConfigurationSection(root);
			if (section == null) continue;
			for (String name : section.getKeys(false)) {
				String id = name.toLowerCase(Locale.ROOT);
				if (!LEGACY_ENTRIES.contains(id)) {
					heads.putIfAbsent(id, root + "." + name);
				}
			}
		}
		return heads;
	}

	/** Advancement links ("advancement criterion") that belong to a head entry and its textures. */
	private static List<String> advancementLinks(FileConfiguration config, String path) {
		List<String> links = new ArrayList<>(config.getStringList(path + ".advancements"));
		ConfigurationSection skulls = config.getConfigurationSection(path + ".skullList");
		if (skulls != null) {
			for (String skull : skulls.getKeys(false)) {
				links.addAll(config.getStringList(path + ".skullList." + skull + ".advancements"));
			}
		}
		return links;
	}

	/** Heads the player has collected (lowercase entry names). */
	public static Set<String> getCollected(Player player) {
		FileConfiguration config = Main.getMyConfig();
		Map<String, String> heads = allHeads(config);
		Set<String> collected = new HashSet<>(getStored(player));

		// Advancement criteria that belong to exactly one head tell us about heads collected earlier.
		Map<String, Set<String>> headsPerLink = new HashMap<>();
		for (Map.Entry<String, String> head : heads.entrySet()) {
			for (String link : advancementLinks(config, head.getValue())) {
				headsPerLink.computeIfAbsent(link.toLowerCase(Locale.ROOT), k -> new HashSet<>()).add(head.getKey());
			}
		}
		Map<String, Collection<String>> awardedCache = new HashMap<>();
		for (Map.Entry<String, Set<String>> entry : headsPerLink.entrySet()) {
			if (entry.getValue().size() != 1) continue;
			String head = entry.getValue().iterator().next();
			if (collected.contains(head)) continue;

			String[] parts = entry.getKey().replace("mobheads:", "").split(" ");
			if (parts.length != 2) continue;
			Collection<String> awarded = awardedCache.computeIfAbsent(parts[0], adv -> awardedCriteria(player, adv));
			for (String criterion : awarded) {
				if (criterion.equalsIgnoreCase(parts[1])) {
					collected.add(head);
					break;
				}
			}
		}

		collected.retainAll(heads.keySet());
		return collected;
	}

	private static Collection<String> awardedCriteria(Player player, String advancementName) {
		Advancement advancement = Bukkit.getAdvancement(new NamespacedKey(Main.getPlugin(), advancementName));
		if (advancement == null) return Set.of();
		AdvancementProgress progress = player.getAdvancementProgress(advancement);
		return progress.getAwardedCriteria();
	}

	/** Builds a written book listing every head, marking the ones the player has. */
	public static ItemStack buildBook(Player player) {
		FileConfiguration config = Main.getMyConfig();
		Map<String, String> heads = allHeads(config);
		Set<String> collected = getCollected(player);

		int total = heads.size();
		int have = collected.size();
		int percent = total == 0 ? 0 : (int) Math.floor(have * 100.0 / total);

		List<String> pages = new ArrayList<>();
		pages.add(color(config.getString("Messages.Collection.Book-title", "&lMob Head Collection"))
				+ "\n\n"
				+ color(config.getString("Messages.Collection.Book-summary",
						"&0You have collected &2[collected]&0 of &2[total]&0 mob heads (&2[percent]%&0).")
						.replace("[collected]", String.valueOf(have))
						.replace("[total]", String.valueOf(total))
						.replace("[percent]", String.valueOf(percent)))
				+ "\n\n"
				+ ChatColor.DARK_GREEN + "✔ " + ChatColor.BLACK + "collected\n"
				+ ChatColor.GRAY + "✘ " + ChatColor.BLACK + "missing");

		StringBuilder page = new StringBuilder();
		int linesUsed = 0;
		for (Map.Entry<String, String> head : heads.entrySet()) {
			String name = prettyName(head.getValue());
			boolean has = collected.contains(head.getKey());
			String line = (has ? ChatColor.DARK_GREEN + "✔ " + ChatColor.BLACK : ChatColor.GRAY + "✘ ") + name;

			int lines = Math.max(1, (int) Math.ceil((name.length() + 2) / (double) CHARS_PER_LINE));
			if (linesUsed + lines > LINES_PER_PAGE && page.length() > 0) {
				pages.add(page.toString());
				page.setLength(0);
				linesUsed = 0;
			}
			if (page.length() > 0) page.append('\n');
			page.append(line);
			linesUsed += lines;
		}
		if (page.length() > 0) pages.add(page.toString());

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.setTitle("Mob Head Collection");
		meta.setAuthor(player.getName());
		meta.setPages(pages);
		book.setItemMeta(meta);
		return book;
	}

	/** "ListOfMobs.Zombie_Villager" -> "Zombie Villager" */
	private static String prettyName(String path) {
		return path.substring(path.lastIndexOf('.') + 1).replace('_', ' ');
	}

	private static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
