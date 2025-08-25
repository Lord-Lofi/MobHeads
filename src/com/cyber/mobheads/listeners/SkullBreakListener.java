package com.cyber.mobheads.listeners;

import com.cyber.mobheads.Config.ConfigController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.util.UUID;

public class SkullBreakListener implements Listener {
    @EventHandler
    public void onSkullDrop(ItemSpawnEvent event) {
        if (event.getEntity().getItemStack().getType() != Material.PLAYER_HEAD) {
            return;
        }

        SkullMeta meta = (SkullMeta) event.getEntity().getItemStack().getItemMeta();

        if (meta.hasLocalizedName()) {
            return;
        }

        // NEW API: Get the PlayerProfile from the SkullMeta
        PlayerProfile profile = meta.getOwnerProfile();

        if (profile == null) {
            return;
        }

        // NEW API: Get the texture from the PlayerProfile
        PlayerTextures textures = profile.getTextures();
        if (textures == null || textures.getSkin() == null) {
            return;
        }

        // The texture URL is in the format: https://textures.minecraft.net/texture/<texture_code>
        String fullUrl = textures.getSkin().toString();
        String code = fullUrl.substring(fullUrl.lastIndexOf("/") + 1);

        if (code == null) {
            return;
        }

        String displayName = ConfigController.getDisplayNameFromTexture(code);

        if (displayName == null) {
            return;
        }

        ItemMeta itemMeta = event.getEntity().getItemStack().getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + displayName);
        event.getEntity().getItemStack().setItemMeta(itemMeta);
    }
}