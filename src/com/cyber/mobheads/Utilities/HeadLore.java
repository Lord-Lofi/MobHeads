package com.cyber.mobheads.Utilities;

import com.cyber.mobheads.Main;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Adds lore such as "Beheaded by Steve" and the date to heads players get from kills.
 * Server owners configure it under head_lore in config.yml; players can turn it off
 * for their own heads with /mobheads headlore.
 */
public final class HeadLore {

	private HeadLore() {}

	private static NamespacedKey key() {
		return new NamespacedKey(Main.getPlugin(), "head_lore");
	}

	/** Whether the server has head lore turned on at all. */
	public static boolean isEnabledOnServer() {
		return Main.getMyConfig().getBoolean("head_lore.enabled", true);
	}

	/** Whether this player's heads get lore (their own choice, or the server default). */
	public static boolean isEnabledFor(Player player) {
		if (!isEnabledOnServer()) return false;
		Byte choice = player.getPersistentDataContainer().get(key(), PersistentDataType.BYTE);
		if (choice == null) {
			return Main.getMyConfig().getBoolean("head_lore.default_on", true);
		}
		return choice == 1;
	}

	public static void setEnabledFor(Player player, boolean enabled) {
		player.getPersistentDataContainer().set(key(), PersistentDataType.BYTE, (byte) (enabled ? 1 : 0));
	}

	/** Adds the kill lore (head_lore.lines) to a head the player got. Does nothing if lore is off for them. */
	public static ItemStack apply(ItemStack head, Player player) {
		return apply(head, player, "head_lore.lines");
	}

	/** Adds the fishing lore (head_lore.fishing_lines) to a head the player fished up. */
	public static ItemStack applyFishing(ItemStack head, Player player) {
		return apply(head, player, "head_lore.fishing_lines");
	}

	private static ItemStack apply(ItemStack head, Player player, String linesPath) {
		if (head == null || player == null || !isEnabledFor(player)) return head;

		FileConfiguration config = Main.getMyConfig();
		String date;
		try {
			date = LocalDate.now().format(DateTimeFormatter.ofPattern(config.getString("head_lore.date_format", "MMM d, yyyy")));
		} catch (IllegalArgumentException e) {
			date = LocalDate.now().toString(); // invalid date_format in config
		}

		List<String> lore = new ArrayList<>();
		for (String line : config.getStringList(linesPath)) {
			lore.add(ChatColor.translateAlternateColorCodes('&',
					line.replace("[player]", player.getName()).replace("[date]", date)));
		}
		if (lore.isEmpty()) return head;

		ItemMeta meta = head.getItemMeta();
		if (meta == null) return head;
		meta.setLore(lore);
		head.setItemMeta(meta);
		return head;
	}
}
