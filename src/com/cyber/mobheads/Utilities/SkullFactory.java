package com.cyber.mobheads.Utilities;

import com.cyber.mobheads.Config.ConfigController;
import com.cyber.mobheads.advancements.AdvancementsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.Base64;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class SkullFactory{

    public static ItemStack getMobSkull(MobMeta mobmeta, Player owner) {
        String encodedTexture = mobmeta.getTextureCode();
        String displayName = mobmeta.getDisplayName();

        if (encodedTexture == null || encodedTexture.equalsIgnoreCase("[vanilla]")) {
            if (owner != null) {
                AdvancementsManager.triggerAdvancement(owner, mobmeta.getAdvancements());
                HeadCollection.record(owner, mobmeta.getMobName());
            }
            if (displayName.equalsIgnoreCase("[vanilla]")) {
                return getVanillaSkull(mobmeta.getMobName());
            }
            ItemStack vanillaSkull = getVanillaSkull(mobmeta.getMobName());
            if (vanillaSkull != null) {
                ItemMeta meta = vanillaSkull.getItemMeta();
                meta.setDisplayName(ChatColor.RESET + displayName);
                vanillaSkull.setItemMeta(meta);
            }
            return vanillaSkull;
        }

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        PlayerProfile profile = Bukkit.createPlayerProfile(getProfileUUID(mobmeta.getUUID(), encodedTexture), "MobHead");
        PlayerTextures textures = profile.getTextures();

        try {
            // Decode Base64 and parse JSON to get the texture URL
            String decodedJson = new String(Base64.getDecoder().decode(encodedTexture));
            JsonObject jsonObject = JsonParser.parseString(decodedJson).getAsJsonObject();
            String textureUrl = jsonObject.getAsJsonObject("textures")
                    .getAsJsonObject("SKIN")
                    .get("url")
                    .getAsString();

            textures.setSkin(new URL(textureUrl));
            profile.setTextures(textures);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        meta.setOwnerProfile(profile);
        meta.setDisplayName(ChatColor.RESET + displayName);

        if (owner != null) {
            AdvancementsManager.triggerAdvancement(owner, mobmeta.getAdvancements());
            HeadCollection.record(owner, mobmeta.getMobName());
        }

        skull.setItemMeta(meta);
        return skull;
    }

    /**
     * Gives each head type a fixed profile UUID so identical heads stack.
     * Uses the config's random_uuid when it is a valid UUID; otherwise (missing,
     * malformed, or an old server config) derives a stable UUID from the texture.
     */
    private static UUID getProfileUUID(String configUUID, String encodedTexture) {
        if (configUUID != null) {
            try {
                return UUID.fromString(configUUID.trim());
            } catch (IllegalArgumentException ignored) {
                // fall through to texture-based UUID
            }
        }
        return UUID.nameUUIDFromBytes(encodedTexture.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public static ItemStack getPlayerSkull(String playername, Player killer) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();

        // New API: Use a PlayerProfile to get the skin from the player's name
        PlayerProfile profile = Bukkit.createPlayerProfile(playername);
        itemMeta.setOwnerProfile(profile);

        item.setItemMeta(itemMeta);
        if (killer != null) AdvancementsManager.triggerAdvancement(killer, ConfigController.getAdvancementsPlayer());
        return item;
    }

    public static ItemStack getVanillaSkull(MobNames mobname) {
        Material mat;
        switch (mobname) {
            case Ender_Dragon:
                mat = Material.DRAGON_HEAD;
                return new ItemStack(mat, 1);
            case Zombie:
                mat = Material.ZOMBIE_HEAD;
                return new ItemStack(mat, 1);
            case Skeleton:
                mat = Material.SKELETON_SKULL;
                return new ItemStack(mat, 1);
            case Creeper:
                mat = Material.CREEPER_HEAD;
                return new ItemStack(mat, 1);
            case Wither_Skeleton:
                mat = Material.WITHER_SKELETON_SKULL;
                return new ItemStack(mat, 1);
        }
        return null;
    }

    public static String getVanillaName(MobNames mobname) {
        switch (mobname) {
            case Skeleton:
                return "Skeleton Head";
            case Creeper:
                return "Creeper Head";
            case Zombie:
                return "Zombie Head";
            case Ender_Dragon:
                return "Dragon Head";
            case Wither_Skeleton:
                return "Wither Skeleton Head";
        }
        return null;
    }

    public static String getVanillaName(Entity entity) {
        switch (entity.getType()) {
            case SKELETON:
                return "Skeleton Head";
            case CREEPER:
                return "Creeper Head";
            case ZOMBIE:
                return "Zombie Head";
            case ENDER_DRAGON:
                return "Dragon Head";
            case WITHER_SKELETON:
                return "Wither Skeleton Head";
        }
        return null;
    }
}