package com.cyber.mobheads.listeners;

import com.cyber.mobheads.Config.ConfigController;
import com.cyber.mobheads.Main;
import com.cyber.mobheads.Utilities.*;
import com.cyber.mobheads.advancements.AdvancementsManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

public class EntityDeathListener
		implements Listener {
	private final List<UUID> killedByChargedCreeper = new ArrayList();


	@EventHandler(priority = EventPriority.MONITOR)
	public void onChargedCreeperDeath(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) event.getEntity();
			if (le.getHealth() - event.getDamage() <= 0.0D &&
					event.getDamager() instanceof Creeper && (
					(Creeper) event.getDamager()).isPowered()) {

				MobNames mobName = MobNames.getName(event.getEntity());
				if ((mobName != null && ConfigController.chargedCreeperDrop(mobName)) || (event
						.getEntity() instanceof Player && ConfigController.chargedCreeperDropForPlayer())) {
					this.killedByChargedCreeper.add(event.getEntity().getUniqueId());
				}
			}
		}
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity livingEntity = event.getEntity();
		Player killer = event.getEntity().getKiller();

		try{
			if(!WorldGuardSupport.canDropHeads(livingEntity.getLocation()))
					return;
		}catch (Error|Exception e4){

		}

		try {
			if (livingEntity instanceof Ageable)
				if (!ConfigController.supportBabies() && !((Ageable) livingEntity).isAdult()) {
					return;
				}
		} catch (ClassCastException classCastException) {
		}


		if (this.killedByChargedCreeper.remove(livingEntity.getUniqueId())) {
			ItemStack skull;
			if (livingEntity instanceof Player) {
				skull = getPlayerHead((Player) livingEntity, null, true);
			} else {
				skull = getMobHead(null, livingEntity, true);
			}
			if (skull == null) {
				return;
			}

			// Minecraft itself drops zombie/skeleton/creeper/etc. heads on charged creeper kills;
			// don't add a second copy of those.
			boolean vanillaAlreadyDropped = false;
			if (skull.getType() != Material.PLAYER_HEAD) {
				for (ItemStack drop : event.getDrops()) {
					if (drop != null && drop.getType() == skull.getType()) {
						vanillaAlreadyDropped = true;
						break;
					}
				}
			}
			if (!vanillaAlreadyDropped) {
				event.getDrops().add(skull);
			}

			// Credit (advancements, collection, broadcast) goes to the nearest player, as before.
			if (!(livingEntity instanceof Player)) {
				new BukkitRunnable() {
					@Override
					public void run() {
						MobNames mobName = MobNames.getName(livingEntity);
						if (mobName == null) return;
						MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobName, false);
						if (mobmeta == null) return;

						Player nearest = null;
						double nearestDistance = Double.MAX_VALUE;
						for (Player player : livingEntity.getWorld().getPlayers()) {
							double distance = player.getLocation().distanceSquared(livingEntity.getLocation());
							if (distance < nearestDistance) {
								nearestDistance = distance;
								nearest = player;
							}
						}
						if (nearest == null) return;

						AdvancementsManager.triggerAdvancement(nearest, mobmeta.getAdvancements());
						HeadCollection.record(nearest, mobName);
						if (mobmeta.isShouldBroadcast()) {
							String name = mobmeta.getUsedDisplayName();
							if (name == null) {
								name = SkullFactory.getVanillaName(livingEntity);
							}
							Broadcaster.broadCastMobHead(nearest, name);
						}
					}
				}.runTaskLater(Main.getPlugin(), 1);
			}
			return;
		}



		if (killer == null)
			return;
		if (killer != null)
			if (livingEntity.getUniqueId().equals(killer.getUniqueId()) &&
					!ConfigController.allow_self_player_head_farming()) {
				return;
			}


		if (livingEntity instanceof Player) {

			ItemStack playerSkull = HeadLore.apply(getPlayerHead((Player) livingEntity, killer, false), killer);
			if (playerSkull == null) {
				return;
			}
			if (ConfigController.dropOutsideInventory()) {
				event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), playerSkull);
			} else {

				event.getDrops().add(playerSkull);
			}

		} else {
			for (ItemStack drops : event.getDrops()) {
				if(drops!=null)
				if (drops.getType() == Material.WITHER_SKELETON_SKULL) {
					MobNames mobname = MobNames.getName(livingEntity);
					MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobname, false);
					//TODO: Check if correct: This will show the advancements, but not duplicate head if already exists
					AdvancementsManager.triggerAdvancement(killer, mobmeta.getAdvancements());
					HeadCollection.record(killer, mobname);

					if (mobmeta.isShouldBroadcast()) {
						String name = mobmeta.getUsedDisplayName();
						if (name == null) {
							name = SkullFactory.getVanillaName(livingEntity);
						}
						Broadcaster.broadCastMobHead(killer, name);
					}
					return;
				}
			}
			ItemStack mobHead = HeadLore.apply(getMobHead(killer, livingEntity, false), killer);
			if (mobHead != null) {
				event.getDrops().add(mobHead);
			}
		}
	}


	private ItemStack getMobHead(Player killer, Entity mob, boolean forceDrop) {
		if (killer != null)
			if (!forceDrop && !killer.hasPermission("com.cyber.mobheads.behead.mobs")) {
				return null;
			}


		MobNames mobName = MobNames.getName(mob);

		if (mobName == null) {
			if (mob.getType().equals(EntityType.WITHER_SKELETON)) {
				return null;
			}

			Broadcaster.outputInfoConsole("The mob \"" +mob.getType().name()+
					"\" is not supported, please update!", 1);
			return null;
		}


		MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobName, false);

		if (!forceDrop) {
			int lootingValue = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
			double dropBonus = mobmeta.getDropBonus();
			double dropChance = mobmeta.getDropChance();

			double chance = dropChance + (lootingValue * dropBonus);


			if (!willDrop(chance)) {
				return null;
			}
		}


		ItemStack skull = SkullFactory.getMobSkull(mobmeta, killer);

		if (mobmeta.isShouldBroadcast() && !forceDrop) {
			String name = mobmeta.getUsedDisplayName();
			if (name == null) {
				name = SkullFactory.getVanillaName(mob);
			}
			Broadcaster.broadCastMobHead(killer, name);
		}
		return skull;
	}


	private ItemStack getPlayerHead(Player deadPlayer, Player killer, boolean forceDrop) {
		if (forceDrop) {
			return SkullFactory.getPlayerSkull(deadPlayer.getName(), killer);
		}

		if (!killer.hasPermission("com.cyber.mobheads.behead.players")) {
			return null;
		}

		int lootingValue = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
		double dropChance = ConfigController.getDropChancePlayer(lootingValue);

		if (willDrop(dropChance)) {
			Broadcaster.broadCastPlayerHead(killer, deadPlayer);
			return SkullFactory.getPlayerSkull(deadPlayer.getName(), killer);
		}
		return null;
	}

	private boolean willDrop(double dropChance) {
		double randomDouble = (new Random()).nextDouble();
		return (randomDouble <= dropChance);
	}

}
