package com.cyber.mobheads.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.Registry;
import org.bukkit.NamespacedKey;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public enum MobNames {
	Allay,
	Armadillo,
	Bat,
	Bee,
	Blaze,
	Bogged,
	Breeze,
	Camel,
	Cave_Spider,
	Creaking,
	Dolphin,
	Donkey,
	Drowned,
	Elder_Guardian,
	Enderman,
	Endermite,
	Ender_Dragon,
	Evoker,
	Ghast,
	Giant,
	Glow_Squid,
	Guardian,
	Happy_Ghast,
	Hoglin,
	Husk,
	Illusioner,
	Iron_Golem,
	Magma_Cube,
	Mooshroom,
	Mooshroom_Brown,
	Mule,
	Ocelot,
	Phantom,
	Piglin,
	Piglin_Brute,
	Pillager,
	Polar_Bear,
	Ravager,
	Shulker,
	Silverfish,
	Skeleton,
	Slime,
	Sniffer,
	Spider,
	Squid,
	Stray,
	Hot_Strider,
    Cold_Strider,
	Tadpole,
	Turtle,
	Vex,
	Vindicator,
	Wandering_Trader,
	Warden,
	Witch,
	Wither,
	Wither_Skeleton,
	Zoglin,
	Zombie,
	Zombie_Pigman,

	//zombie villager types
	Zombie_Villager,
	Zombie_Armorer_Villager,
	Zombie_Butcher_Villager,
	Zombie_Cartographer_Villager,
	Zombie_Cleric_Villager,
	Zombie_Farmer_Villager,
	Zombie_Fisherman_Villager,
	Zombie_Fletcher_Villager,
	Zombie_Leatherworker_Villager,
	Zombie_Librarian_Villager,
	Zombie_Mason_Villager,
	Zombie_Nitwit_Villager,
	Zombie_Shepherd_Villager,
	Zombie_Toolsmith_Villager,
	Zombie_Weaponsmith_Villager,

	//villager types
	Villager,
	Villager_Armorer,
	Villager_Butcher,
	Villager_Cartographer,
	Villager_Cleric,
	Villager_Farmer,
	Villager_Fisherman,
	Villager_Fletcher,
	Villager_Leatherworker,
	Villager_Librarian,
	Villager_Mason,
	Villager_Nitwit,
	Villager_Shepherd,
	Villager_Toolsmith,
	Villager_Weaponsmith,

	//goat types
	Goat,
	Screaming_Goat,

	//snow_golem types
	Snow_Golem,
	Derpy_Snow_Golem,

	//creeper types
	Creeper,
	Charged_Creeper,

	//cow types
	Cow,
	Temperate_Cow,
	Cold_Cow,
	Warm_Cow,

	//chicken types
	Chicken,
	Temperate_Chicken,
	Cold_Chicken,
	Warm_Chicken,

	//wolf types
	Wild_Wolf,
	Tamed_Wolf,
	Tamed_Ashen_Wolf,
	Tamed_Black_Wolf,
	Tamed_Chestnut_Wolf,
	Tamed_Pale_Wolf,
	Tamed_Rusty_Wolf,
	Tamed_Snowy_Wolf,
	Tamed_Spotted_Wolf,
	Tamed_Striped_Wolf,
	Tamed_Woods_Wolf,
	Wild_Ashen_Wolf,
	Wild_Black_Wolf,
	Wild_Chestnut_Wolf,
	Wild_Pale_Wolf,
	Wild_Rusty_Wolf,
	Wild_Snowy_Wolf,
	Wild_Spotted_Wolf,
	Wild_Striped_Wolf,
	Wild_Woods_Wolf,

	//pig types
	Pig,
	Temperate_Pig,
	Cold_Pig,
	Warm_Pig,

	//frog types
	Frog,
	Temperate_Frog,
	Cold_Frog,
	Warm_Frog,
	
	//axolotl types
	Axolotl,
	Blue_Axolotl,
	Cyan_Axolotl,
	Gold_Axolotl,
	Lucy_Axolotl,
	Wild_Axolotl,

	//llama types
	Brown_Llama,
	Creamy_Llama,
	Gray_Llama,
	White_Llama,
	Brown_Llama_Trader,
	Creamy_Llama_Trader,
	Gray_Llama_Trader,
	White_Llama_Trader,

	//rabbit types
	Black_Rabbit,
	Black_and_White_Rabbit,
	Brown_Rabbit,
	Gold_Rabbit,
	Salt_and_Pepper_Rabbit,
	The_Killer_Bunny,
	Toast_Rabbit,
	White_Rabbit,
	
	//fish types
	Tropical_Fish,
	Cod,
	Salmon,
	Pufferfish,

	//parrot types
	Blue_Parrot,
	Cyan_Parrot,
	Gray_Parrot,
	Green_Parrot,
	Red_Parrot,
	
	//horse types
	Black_Horse,
	Brown_Horse,
	Chestnut_Horse,
	Creamy_Horse,
	Dark_Brown_Horse,
	Gray_Horse,
	White_Horse,
	Skeleton_Horse,
	Zombie_Horse,

	//sheep types
	Black_Sheep,
	Blue_Sheep,
	Brown_Sheep,
	Cyan_Sheep,
	Gray_Sheep,
	Green_Sheep,
	Light_Blue_Sheep,
	Lime_Sheep,
	Magenta_Sheep,
	Orange_Sheep,
	Pink_Sheep,
	Purple_Sheep,
	Rainbow_Sheep,
	Red_Sheep,
	Light_Gray_Sheep,
	White_Sheep,
	Yellow_Sheep,
	
	//panda types
	Panda_Normal,
	Panda_Aggressive,
	Panda_Lazy,
	Panda_Brown,
	Panda_Worried,
	Panda_Playful,
	Panda_Weak,

	//fox types
	Fox_Normal,
	Fox_Snow,

	//copper golem types
	Copper_Golem,
	Exposed_Copper_Golem,
	Oxidized_Copper_Golem,
	Weathered_Copper_Golem,

	//nautilus types
	Nautilus,
	Temperate_Zombie_Nautilus,
	Warm_Zombie_Nautilus,

	//1.21.11 new mobs
	Parched,
	Camel_Husk,

	//26.x new mobs
	Sulfur_Cube,

	//These are the invalid cats. Still have to load them:
	Black_Cat,
	Ginger_Cat,
	Siamese_Cat,

	//cat types
	Cat_AllBack,
	Cat_Black,
	Cat_British_ShortHair,
	Cat_Calico,
	Cat_Jellie,
	Cat_Persian,
	Cat_Ragdoll,
	Cat_Red,
	Cat_Siamese,
	Cat_Tabby,
	Cat_White,
	;


	/**
	 * Which head each entity type drops. Types with variants (frogs, cats, ...)
	 * pick the right head from the entity itself. This table is the single
	 * source of truth: getName() uses it to pick a head, hasHead() to check coverage.
	 */
	private static final Map<EntityType, Function<Entity, MobNames>> HEADS = new EnumMap<>(EntityType.class);
	static {
		HEADS.put(EntityType.ARMADILLO, entity -> Armadillo);
		HEADS.put(EntityType.BAT, entity -> Bat);
		HEADS.put(EntityType.BLAZE, entity -> Blaze);
		HEADS.put(EntityType.BOGGED, entity -> Bogged);
		HEADS.put(EntityType.BREEZE, entity -> Breeze);
		HEADS.put(EntityType.OCELOT, entity -> Ocelot);
		HEADS.put(EntityType.CAMEL, entity -> Camel);
		HEADS.put(EntityType.CAMEL_HUSK, entity -> Camel_Husk);
		HEADS.put(EntityType.CAVE_SPIDER, entity -> Cave_Spider);
		HEADS.put(EntityType.CHICKEN, entity -> getChickenName((Chicken) entity));
		HEADS.put(EntityType.COPPER_GOLEM, entity -> getCopperGolemName(entity));
		HEADS.put(EntityType.COW, entity -> getCowName((Cow) entity));
		HEADS.put(EntityType.CREAKING, entity -> Creaking);
		HEADS.put(EntityType.CREEPER, entity -> getCreeperName((Creeper) entity));
		HEADS.put(EntityType.DOLPHIN, entity -> Dolphin);
		HEADS.put(EntityType.DONKEY, entity -> Donkey);
		HEADS.put(EntityType.DROWNED, entity -> Drowned);
		HEADS.put(EntityType.ELDER_GUARDIAN, entity -> Elder_Guardian);
		HEADS.put(EntityType.ENDERMAN, entity -> Enderman);
		HEADS.put(EntityType.ENDERMITE, entity -> Endermite);
		HEADS.put(EntityType.ENDER_DRAGON, entity -> Ender_Dragon);
		HEADS.put(EntityType.EVOKER, entity -> Evoker);
		HEADS.put(EntityType.GHAST, entity -> Ghast);
		HEADS.put(EntityType.GIANT, entity -> Giant);
		HEADS.put(EntityType.GUARDIAN, entity -> Guardian);
		HEADS.put(EntityType.HAPPY_GHAST, entity -> Happy_Ghast);
		HEADS.put(EntityType.HORSE, entity -> getHorseName((Horse) entity));
		HEADS.put(EntityType.ZOMBIE_HORSE, entity -> Zombie_Horse);
		HEADS.put(EntityType.SKELETON_HORSE, entity -> Skeleton_Horse);
		HEADS.put(EntityType.HUSK, entity -> Husk);
		HEADS.put(EntityType.ILLUSIONER, entity -> Illusioner);
		HEADS.put(EntityType.IRON_GOLEM, entity -> Iron_Golem);
		HEADS.put(EntityType.LLAMA, entity -> getLLamaName((Llama) entity));
		HEADS.put(EntityType.TRADER_LLAMA, entity -> getLLamaTraderName((TraderLlama) entity));
		HEADS.put(EntityType.MAGMA_CUBE, entity -> Magma_Cube);
		HEADS.put(EntityType.MOOSHROOM, entity -> getMooshroomName((MushroomCow) entity));
		HEADS.put(EntityType.MULE, entity -> Mule);
		HEADS.put(EntityType.PARROT, entity -> getParrotName((Parrot) entity));
		HEADS.put(EntityType.PHANTOM, entity -> Phantom);
		HEADS.put(EntityType.PIG, entity -> getPigName((Pig) entity));
		HEADS.put(EntityType.POLAR_BEAR, entity -> Polar_Bear);
		HEADS.put(EntityType.RABBIT, entity -> getRabbitName((Rabbit) entity));
		HEADS.put(EntityType.SHEEP, entity -> getSheepName((Sheep) entity));
		HEADS.put(EntityType.SHULKER, entity -> Shulker);
		HEADS.put(EntityType.SILVERFISH, entity -> Silverfish);
		HEADS.put(EntityType.SKELETON, entity -> Skeleton);
		HEADS.put(EntityType.SLIME, entity -> Slime);
		HEADS.put(EntityType.SNIFFER, entity -> Sniffer);
		HEADS.put(EntityType.SNOW_GOLEM, entity -> getSnowGolemName((Snowman) entity));
		HEADS.put(EntityType.SPIDER, entity -> Spider);
		HEADS.put(EntityType.SQUID, entity -> Squid);
		HEADS.put(EntityType.NAUTILUS, entity -> Nautilus);
		HEADS.put(EntityType.ZOMBIE_NAUTILUS, entity -> getZombieNautilusName((ZombieNautilus) entity));
		HEADS.put(EntityType.PARCHED, entity -> Parched);
		HEADS.put(EntityType.SULFUR_CUBE, entity -> Sulfur_Cube);
		HEADS.put(EntityType.STRAY, entity -> Stray);
		HEADS.put(EntityType.TURTLE, entity -> Turtle);
		HEADS.put(EntityType.VEX, entity -> Vex);
		HEADS.put(EntityType.VILLAGER, entity -> getVillagerName((Villager)entity));
		HEADS.put(EntityType.VINDICATOR, entity -> Vindicator);
		HEADS.put(EntityType.WITCH, entity -> Witch);
		HEADS.put(EntityType.WITHER, entity -> Wither);
		HEADS.put(EntityType.WITHER_SKELETON, entity -> Wither_Skeleton);
		HEADS.put(EntityType.WOLF, entity -> getWolfName((Wolf) entity));
		HEADS.put(EntityType.ZOMBIE, entity -> Zombie);
		HEADS.put(EntityType.ZOMBIE_VILLAGER, entity -> getZombieVillagerName((ZombieVillager) entity));
		HEADS.put(EntityType.COD, entity -> Cod);
		HEADS.put(EntityType.PUFFERFISH, entity -> Pufferfish);
		HEADS.put(EntityType.SALMON, entity -> Salmon);
		HEADS.put(EntityType.TROPICAL_FISH, entity -> Tropical_Fish);
		HEADS.put(EntityType.ARROW, entity -> getFishName(entity));
		HEADS.put(EntityType.WANDERING_TRADER, entity -> Wandering_Trader);
		HEADS.put(EntityType.PILLAGER, entity -> Pillager);
		HEADS.put(EntityType.RAVAGER, entity -> Ravager);
		HEADS.put(EntityType.CAT, entity -> getCatName((Cat) entity));
		HEADS.put(EntityType.PANDA, entity -> getPandaName((Panda) entity));
		HEADS.put(EntityType.BEE, entity -> Bee);
		HEADS.put(EntityType.FOX, entity -> getFoxName((Fox) entity));
		HEADS.put(EntityType.ZOMBIFIED_PIGLIN, entity -> Zombie_Pigman);
		HEADS.put(EntityType.PIGLIN, entity -> Piglin);
		HEADS.put(EntityType.PIGLIN_BRUTE, entity -> Piglin_Brute);
		HEADS.put(EntityType.STRIDER, entity -> getStriderName((Strider) entity));
		HEADS.put(EntityType.ZOGLIN, entity -> Zoglin);
		HEADS.put(EntityType.HOGLIN, entity -> Hoglin);
		HEADS.put(EntityType.ALLAY, entity -> Allay);
		HEADS.put(EntityType.WARDEN, entity -> Warden);
		HEADS.put(EntityType.FROG, entity -> getFrogName((Frog) entity));
		HEADS.put(EntityType.TADPOLE, entity -> Tadpole);
		HEADS.put(EntityType.GLOW_SQUID, entity -> Glow_Squid);
		HEADS.put(EntityType.AXOLOTL, entity -> getAxolotlName((Axolotl) entity));
		HEADS.put(EntityType.GOAT, entity -> getGoatName((Goat) entity));
	}

	public static MobNames getName(Entity entity) {
		if(entity.getType().name().equals("PIG_ZOMBIE"))
			return Zombie_Pigman;

		Function<Entity, MobNames> head = HEADS.get(entity.getType());
		return head == null ? null : head.apply(entity);
	}

	/** True if this plugin has a head for the given entity type. */
	public static boolean hasHead(EntityType type) {
		return HEADS.containsKey(type);
	}

	private static MobNames getCatName(Cat ocelot) {
		if (ocelot.getCatType() == null) {
			return null;
		}
		if (ocelot.getCatType().equals(Cat.Type.ALL_BLACK)) {
			return Cat_AllBack;
		}
		if (ocelot.getCatType().equals(Cat.Type.BRITISH_SHORTHAIR)) {
			return Cat_British_ShortHair;
		}
		if (ocelot.getCatType().equals(Cat.Type.SIAMESE)) {
			return Cat_Siamese;
		}
		if (ocelot.getCatType().equals(Cat.Type.RAGDOLL)) {
			return Cat_Ragdoll;
		}
		if (ocelot.getCatType().equals(Cat.Type.PERSIAN)) {
			return Cat_Persian;
		}
		if (ocelot.getCatType().equals(Cat.Type.JELLIE)) {
			return Cat_Jellie;
		}
		if (ocelot.getCatType().equals(Cat.Type.CALICO)) {
			return Cat_Calico;
		}
		if (ocelot.getCatType().equals(Cat.Type.WHITE)) {
			return Cat_White;
		}
		if (ocelot.getCatType().equals(Cat.Type.TABBY)) {
			return Cat_Tabby;
		}
		if (ocelot.getCatType().equals(Cat.Type.BLACK)) {
			return Cat_Black;
		}
		if (ocelot.getCatType().equals(Cat.Type.RED)) {
			return Cat_Red;
		}

		return null;

	}
	private static MobNames getFoxName(Fox fox) {

		if (fox.getFoxType() == null) {
			return null;
		}
		switch (fox.getFoxType()) {
			case RED:
				return Fox_Normal;
			case SNOW:
				return Fox_Snow;
		}
		return null;

	}
	private static MobNames getCowName(Cow cow) {
		// Get the NamespacedKey of the cow's variant
		final NamespacedKey cowVariantKey = cow.getVariant().getKey();

		// Define the NamespacedKeys for comparison
		final NamespacedKey coldKey = new NamespacedKey("minecraft", "cold");
		final NamespacedKey temperateKey = new NamespacedKey("minecraft", "temperate");
		final NamespacedKey warmKey = new NamespacedKey("minecraft", "warm");

		// Get the variants from the registry using their NamespacedKeys and compare
        if (cowVariantKey.equals(new NamespacedKey("minecraft", "cold"))) {
            return MobNames.Cold_Cow;
        }
        if (cowVariantKey.equals(new NamespacedKey("minecraft", "temperate"))) {
            return MobNames.Temperate_Cow;
        }
        if (cowVariantKey.equals(new NamespacedKey("minecraft", "warm"))) {
            return MobNames.Warm_Cow;
        }
		return MobNames.Temperate_Cow;
	}
	private static MobNames getChickenName(Chicken chicken) {
		// Get the NamespacedKey of the chicken's variant
		final NamespacedKey chickenVariantKey = chicken.getVariant().getKey();

		// Define the NamespacedKeys for comparison
		final NamespacedKey coldKey = new NamespacedKey("minecraft", "cold");
		final NamespacedKey temperateKey = new NamespacedKey("minecraft", "temperate");
		final NamespacedKey warmKey = new NamespacedKey("minecraft", "warm");

		// Get the variants from the registry using their NamespacedKeys and compare
        if (chickenVariantKey.equals(new NamespacedKey("minecraft", "cold"))) {
            return MobNames.Cold_Chicken;
        }
        if (chickenVariantKey.equals(new NamespacedKey("minecraft", "temperate"))) {
            return MobNames.Temperate_Chicken;
        }
        if (chickenVariantKey.equals(new NamespacedKey("minecraft", "warm"))) {
            return MobNames.Warm_Chicken;
        }
        return MobNames.Temperate_Chicken;
    }
	private static MobNames getPigName(Pig pig) {
		// Get the NamespacedKey of the pig's variant using the getKey() method
		final NamespacedKey pigVariantKey = pig.getVariant().getKey();

		// Define the NamespacedKeys for comparison
		final NamespacedKey coldKey = new NamespacedKey("minecraft", "cold");
		final NamespacedKey temperateKey = new NamespacedKey("minecraft", "temperate");
		final NamespacedKey warmKey = new NamespacedKey("minecraft", "warm");

		// Get the variants from the registry using their NamespacedKeys and compare
        if (pigVariantKey.equals(new NamespacedKey("minecraft", "cold"))) {
            return MobNames.Cold_Pig;
        }
        if (pigVariantKey.equals(new NamespacedKey("minecraft", "temperate"))) {
            return MobNames.Temperate_Pig;
        }
        if (pigVariantKey.equals(new NamespacedKey("minecraft", "warm"))) {
            return MobNames.Warm_Pig;
        }
        return MobNames.Temperate_Pig;
	}
	private static MobNames getFrogName(Frog frog) {
		// Get the NamespacedKey of the frog's variant using the getKey() method
		final NamespacedKey frogVariantKey = frog.getVariant().getKey();

		// Define the NamespacedKeys for comparison
		final NamespacedKey coldKey = new NamespacedKey("minecraft", "cold");
		final NamespacedKey temperateKey = new NamespacedKey("minecraft", "temperate");
		final NamespacedKey warmKey = new NamespacedKey("minecraft", "warm");

		// Get the variants from the registry using their NamespacedKeys and compare
		if (frogVariantKey.equals(Registry.FROG_VARIANT.get(coldKey).getKey())) {
			return MobNames.Cold_Frog;
		}
		if (frogVariantKey.equals(Registry.FROG_VARIANT.get(temperateKey).getKey())) {
			return MobNames.Temperate_Frog;
		}
		if (frogVariantKey.equals(Registry.FROG_VARIANT.get(warmKey).getKey())) {
			return MobNames.Warm_Frog;
		}
		return MobNames.Temperate_Frog;
	}

	private static MobNames getAxolotlName(Axolotl axolotl) {
		if (axolotl.getVariant() == null) {
			return null;
		}
		switch (axolotl.getVariant()) {
			case BLUE:
				return Blue_Axolotl;
			case CYAN:
				return Cyan_Axolotl;
			case GOLD:
				return Gold_Axolotl;
			case LUCY:
				return Lucy_Axolotl;
			case WILD:
				return Wild_Axolotl;
		}
		return null;
	}

	private static MobNames getMooshroomName(MushroomCow mooshroom) {
		if (mooshroom.getVariant() == MushroomCow.Variant.BROWN) {
			return Mooshroom_Brown;
		} else {
			return Mooshroom;
		}
	}

	private static MobNames getCreeperName(Creeper creeper) {
		if (creeper.isPowered()) {
			return Charged_Creeper;
		}
		return Creeper;
	}

	private static MobNames getGoatName(Goat goat) {
		if (goat.isScreaming()) {
			return Screaming_Goat;
		}
		return Goat;
	}

    private static MobNames getStriderName(Strider strider) {
        if (strider.isShivering()) {
            return Cold_Strider;
        }
        return Hot_Strider;
    }

	private static MobNames getSnowGolemName(Snowman snowgolem) {
		if (snowgolem.isDerp()) {
			return Derpy_Snow_Golem;
		}
		return Snow_Golem;
	}

	private static MobNames getCopperGolemName(Entity entity) {
		try {
			// Paper API: getWeatheringState()
			Object state = entity.getClass().getMethod("getWeatheringState").invoke(entity);
			switch (state.toString()) {
				case "EXPOSED":   return Exposed_Copper_Golem;
				case "WEATHERED": return Weathered_Copper_Golem;
				case "OXIDIZED":  return Oxidized_Copper_Golem;
				default:          return Copper_Golem;
			}
		} catch (NoSuchMethodException e) {
			try {
				// Spigot API fallback: getWeatherState()
				Object state = entity.getClass().getMethod("getWeatherState").invoke(entity);
				switch (state.toString()) {
					case "EXPOSED":   return Exposed_Copper_Golem;
					case "WEATHERED": return Weathered_Copper_Golem;
					case "OXIDIZED":  return Oxidized_Copper_Golem;
					default:          return Copper_Golem;
				}
			} catch (Exception ex) {
				return Copper_Golem;
			}
		} catch (Exception e) {
			return Copper_Golem;
		}
	}

	private static MobNames getHorseName(Horse horse) {
		if (horse.getColor() == null) {
			return null;
		}
		switch (horse.getColor()) {
			case BLACK:
				return Black_Horse;
			case BROWN:
				return Brown_Horse;
			case CHESTNUT:
				return Chestnut_Horse;
			case CREAMY:
				return Creamy_Horse;
			case DARK_BROWN:
				return Dark_Brown_Horse;
			case GRAY:
				return Gray_Horse;
			case WHITE:
				return White_Horse;
		}
		return null;
	}

	private static MobNames getLLamaName(Llama llama) {
		if (llama.getColor() == null) {
			return null;
		}
		switch (llama.getColor()) {
			case BROWN:
				return Brown_Llama;
			case CREAMY:
				return Creamy_Llama;
			case GRAY:
				return Gray_Llama;
			case WHITE:
				return White_Llama;
		}
		return null;
	}
	private static MobNames getLLamaTraderName(TraderLlama llama) {
		if (llama.getColor() == null) {
			return null;
		}
		switch (llama.getColor()) {
			case BROWN:
				return Brown_Llama_Trader;
			case CREAMY:
				return Creamy_Llama_Trader;
			case GRAY:
				return Gray_Llama_Trader;
			case WHITE:
				return White_Llama_Trader;
		}
		return null;
	}

	private static MobNames getParrotName(Parrot parrot) {
		if (parrot.getVariant() == null) {
			return null;
		}
		switch (parrot.getVariant()) {
			case BLUE:
				return Blue_Parrot;
			case CYAN:
				return Cyan_Parrot;
			case GRAY:
				return Gray_Parrot;
			case GREEN:
				return Green_Parrot;
			case RED:
				return Red_Parrot;
		}
		return null;
	}

	private static MobNames getRabbitName(Rabbit rabbit) {
		if (rabbit.getCustomName() != null && rabbit.getName().equals("Toast")) {
			return Toast_Rabbit;
		}
		if (rabbit.getRabbitType() == null) {
			return null;
		}
		switch (rabbit.getRabbitType()) {
			case BLACK:
				return Black_Rabbit;
			case BLACK_AND_WHITE:
				return Black_and_White_Rabbit;
			case BROWN:
				return Brown_Rabbit;
			case GOLD:
				return Gold_Rabbit;
			case SALT_AND_PEPPER:
				return Salt_and_Pepper_Rabbit;
			case THE_KILLER_BUNNY:
				return The_Killer_Bunny;
			case WHITE:
				return White_Rabbit;
		}
		return null;
	}

	private static MobNames getPandaName(Panda rabbit) {
		if (rabbit.getMainGene() == null) {
			return null;
		}
		switch (rabbit.getMainGene()) {
			case LAZY:
				return Panda_Lazy;
			case WEAK:
				return Panda_Weak;
			case BROWN:
				return Panda_Brown;
			case NORMAL:
				return Panda_Normal;
			case PLAYFUL:
				return Panda_Playful;
			case WORRIED:
				return Panda_Worried;
			case AGGRESSIVE:
				return Panda_Aggressive;
		}
		return null;
	}

	private static MobNames getSheepName(Sheep sheep) {
		if (sheep.getCustomName() != null && sheep.getCustomName().equals("jeb_")) {
			return Rainbow_Sheep;
		}
		if (sheep.getColor() == null) {
			return null;
		}
		switch (sheep.getColor()) {
			case BLACK:
				return Black_Sheep;
			case BLUE:
				return Blue_Sheep;
			case BROWN:
				return Brown_Sheep;
			case CYAN:
				return Cyan_Sheep;
			case GRAY:
				return Gray_Sheep;
			case GREEN:
				return Green_Sheep;
			case LIGHT_BLUE:
				return Light_Blue_Sheep;
			case LIME:
				return Lime_Sheep;
			case MAGENTA:
				return Magenta_Sheep;
			case ORANGE:
				return Orange_Sheep;
			case PINK:
				return Pink_Sheep;
			case PURPLE:
				return Purple_Sheep;
			case RED:
				return Red_Sheep;
			case LIGHT_GRAY:
				return Light_Gray_Sheep;
			case WHITE:
				return White_Sheep;
			case YELLOW:
				return Yellow_Sheep;
		}
		return null;
	}

	private static MobNames getWolfName(Wolf wolf) {
		if (wolf.isTamed()) {
			if (wolf.getVariant() == null) {
				return Tamed_Wolf;
			}
			final NamespacedKey wolfVariantKey = wolf.getVariant().getKey();

			// Compare the key to the registry variants
			if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "ashen")).getKey())) {
				return MobNames.Tamed_Ashen_Wolf;
			}
			if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "black")).getKey())) {
				return MobNames.Tamed_Black_Wolf;
			}
			if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "chestnut")).getKey())) {
				return MobNames.Tamed_Chestnut_Wolf;
			}
						if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "pale")).getKey())) {
				return MobNames.Tamed_Pale_Wolf;
			}
						if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "rusty")).getKey())) {
				return MobNames.Tamed_Rusty_Wolf;
			}
						if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "snowy")).getKey())) {
				return MobNames.Tamed_Snowy_Wolf;
			}
						if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "spotted")).getKey())) {
				return MobNames.Tamed_Spotted_Wolf;
			}
						if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "striped")).getKey())) {
				return MobNames.Tamed_Striped_Wolf;
			}
						if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "woods")).getKey())) {
				return MobNames.Tamed_Woods_Wolf;
			}
			// Default return if no match is found
			return Tamed_Wolf;
		}
		if (wolf.getVariant() == null) {
			return Wild_Wolf;
		}
		final NamespacedKey wolfVariantKey = wolf.getVariant().getKey();

		// Compare the key to the registry variants
		if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "ashen")).getKey())) {
			return MobNames.Wild_Ashen_Wolf;
		}
		if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "black")).getKey())) {
			return MobNames.Wild_Black_Wolf;
		}
		if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "chestnut")).getKey())) {
			return MobNames.Wild_Chestnut_Wolf;
		}
					if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "pale")).getKey())) {
			return MobNames.Wild_Pale_Wolf;
		}
					if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "rusty")).getKey())) {
			return MobNames.Wild_Rusty_Wolf;
		}
					if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "snowy")).getKey())) {
			return MobNames.Wild_Snowy_Wolf;
		}
					if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "spotted")).getKey())) {
			return MobNames.Wild_Spotted_Wolf;
		}
					if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "striped")).getKey())) {
			return MobNames.Wild_Striped_Wolf;
		}
					if (wolfVariantKey.equals(Registry.WOLF_VARIANT.get(new NamespacedKey("minecraft", "woods")).getKey())) {
			return MobNames.Wild_Woods_Wolf;
		}
		// Default return if no match is found
		return Wild_Wolf;
	}

	private static MobNames getZombieVillagerName(ZombieVillager zombieVillager) {
		if (zombieVillager.getVillagerProfession() == null) {
			return Zombie_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.ARMORER)) {
			return Zombie_Armorer_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.BUTCHER)) {
			return Zombie_Butcher_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.CARTOGRAPHER)) {
			return Zombie_Cartographer_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.CLERIC)) {
			return Zombie_Cleric_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.FARMER)) {
			return Zombie_Farmer_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.FISHERMAN)) {
			return Zombie_Fisherman_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.FLETCHER)) {
			return Zombie_Fletcher_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.LEATHERWORKER)) {
			return Zombie_Leatherworker_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.LIBRARIAN)) {
			return Zombie_Librarian_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.MASON)) {
			return Zombie_Mason_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.NITWIT)) {
			return Zombie_Nitwit_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.SHEPHERD)) {
			return Zombie_Shepherd_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.TOOLSMITH)) {
			return Zombie_Toolsmith_Villager;
		}
		if (zombieVillager.getVillagerProfession().equals(Profession.WEAPONSMITH)) {
			return Zombie_Weaponsmith_Villager;
		}
		return Zombie_Villager;
	}


	private static MobNames getVillagerName(Villager villager) {
		if (villager.getProfession() == null) {
			return Villager;
		}
		if (villager.getProfession().equals(Profession.ARMORER)) {
			return Villager_Armorer;
		}
		if (villager.getProfession().equals(Profession.BUTCHER)) {
			return Villager_Butcher;
		}
		if (villager.getProfession().equals(Profession.CARTOGRAPHER)) {
			return Villager_Cartographer;
		}
		if (villager.getProfession().equals(Profession.CLERIC)) {
			return Villager_Cleric;
		}
		if (villager.getProfession().equals(Profession.FARMER)) {
			return Villager_Farmer;
		}
		if (villager.getProfession().equals(Profession.FISHERMAN)) {
			return Villager_Fisherman;
		}
		if (villager.getProfession().equals(Profession.FLETCHER)) {
			return Villager_Fletcher;
		}
		if (villager.getProfession().equals(Profession.LEATHERWORKER)) {
			return Villager_Leatherworker;
		}
		if (villager.getProfession().equals(Profession.LIBRARIAN)) {
			return Villager_Librarian;
		}
		if (villager.getProfession().equals(Profession.MASON)) {
			return Villager_Mason;
		}
		if (villager.getProfession().equals(Profession.NITWIT)) {
			return Villager_Nitwit;
		}
		if (villager.getProfession().equals(Profession.SHEPHERD)) {
			return Villager_Shepherd;
		}
		if (villager.getProfession().equals(Profession.TOOLSMITH)) {
			return Villager_Toolsmith;
		}
		if (villager.getProfession().equals(Profession.WEAPONSMITH)) {
			return Villager_Weaponsmith;
		}
		return Villager;
	}

	private static MobNames getZombieNautilusName(ZombieNautilus zombieNautilus) {
		if (zombieNautilus.getVariant() == null) {
			return Temperate_Zombie_Nautilus;
		}
		if (zombieNautilus.getVariant() == ZombieNautilus.Variant.TEMPERATE) {
			return Temperate_Zombie_Nautilus;
		}
		if (zombieNautilus.getVariant() == ZombieNautilus.Variant.WARM) {
			return Warm_Zombie_Nautilus;
		}
		return Temperate_Zombie_Nautilus;
	}

	private static MobNames getFishName(Entity entity) {
		if (entity.getName() == null) {
			return null;
		}
		switch (entity.getName()) {
			case "Raw Cod":
				return Cod;
			case "Raw Salmon":
				return Salmon;
			case "Pufferfish":
				return Pufferfish;
			case "Tropical Fish":
				return Tropical_Fish;
		}
		return null;
	}
}

