# MobHeads

A Paper plugin that adds custom player-skull drops for nearly every mob in the game — including variant-aware drops for mobs like wolves, frogs, pandas, copper golems, and more. Heads are fully configurable with custom textures, drop chances, looting bonuses, broadcast messages, and advancement support.

> Releases are available on the right-hand side of this page under **Releases**.

---

## Features

- **200+ unique mob heads** — including per-variant drops for wolves, frogs, sheep, horses, villagers, axolotls, pandas, cats, and more
- **Variant-aware drops** — a weathered copper golem drops a different head than an unaffected one; a screaming goat drops a different head than a regular goat
- **Fully configurable** — set drop chance, looting bonus, display name, texture, broadcast message, and advancement triggers per mob
- **Advancement integration** — grant players in-game advancements when they collect specific heads
- **Charged creeper support** — guaranteed drops when a mob is killed by a charged creeper (configurable per mob)
- **Fish heads** — obtainable while fishing, with Luck of the Sea bonus support
- **WorldGuard support** — respects region flags
- **bStats metrics**

---

## Requirements

| Requirement | Version |
|---|---|
| Server software | [Paper](https://papermc.io) |
| Minecraft | 26.3 |
| Java | 25+ |

> This plugin targets Paper specifically. It will not work correctly on vanilla Spigot due to API differences introduced in 1.21.10+.

---

## Installation

1. Download the latest release JAR from the **Releases** section
2. Drop it into your server's `plugins/` folder
3. Restart the server — a default `config.yml` will be generated
4. Adjust drop chances, textures, and other settings to your liking
5. Use `/mh reload` to apply config changes without restarting

**Upgrading:** just replace the jar. On startup the plugin updates your existing `config.yml` — adding new mobs and settings, new advancement links, and fixing invalid head UUIDs — while keeping your own drop chances, names and other values. A backup of the old file is saved as `config.yml.backup-v<old version>`.

The advancements data pack is installed automatically into your main world's `datapacks/mobheads` folder, and updated whenever a new plugin version includes changes to it. Set `auto_update_achievements: false` in `config.yml` to manage it yourself, or run `/mh initachievements` to force a reinstall.

---

## Commands

| Command | Description |
|---|---|
| `/mh collection` | Opens a book showing which mob heads you've collected and which are missing |
| `/mh collection <player>` | Shows another online player's collection |
| `/mh headlore [on\|off]` | Turns the "Beheaded by" lore on your heads on or off (toggles with no argument) |

---

## Permissions

| Permission | Default | Description |
|---|---|---|
| `com.cyber.mobheads.behead.mobs` | op | Allows the player to receive mob heads on kill |
| `com.cyber.mobheads.behead.players` | op | Allows the player to receive player heads on kill |
| `com.cyber.mobheads.collection` | everyone | Use `/mh collection` |
| `com.cyber.mobheads.collection.others` | op | Use `/mh collection <player>` |
| `com.cyber.mobheads.headlore` | everyone | Use `/mh headlore` |

---

## Configuration

The config is split into two sections:

- **`ListOfMobs`** — all killable mob heads
- **`ListOfFish`** — fish heads obtainable through fishing

Each entry supports the following fields:

```yaml
Mob_Name:
  dropChance: 0.25%        # Chance the head drops on kill (use DEFAULT to inherit global setting)
  lootingBonus: 0.1%       # Added chance per looting level (use DEFAULT to inherit)
  dropOnChargedCreeperDeath: true  # Whether a charged creeper guarantees the drop
  broadcastMessage: true   # Announce the drop to the server
  displayName: "&eMob Head"
  random_uuid: <uuid>
  textureCode: <base64>
  advancements:
    - "mobheads:category criteria_name"
```

Set `dropChance: NONE` to disable a specific head without removing the entry.

---

## Supported Mobs

<details>
  <summary>Click to expand the full mob list</summary>

```
Allay
Armadillo
Bat
Bee
Blaze
Bogged
Breeze
Camel
Camel_Husk
Cave_Spider
Creaking
Dolphin
Donkey
Drowned
Elder_Guardian
Enderman
Endermite
Ender_Dragon
Evoker
Ghast
Giant
Glow_Squid
Guardian
Happy_Ghast
Hoglin
Husk
Illusioner
Iron_Golem
Magma_Cube
Mooshroom
Mooshroom_Brown
Mule
Nautilus
Ocelot
Parched
Phantom
Piglin
Piglin_Brute
Pillager
Polar_Bear
Ravager
Shulker
Silverfish
Skeleton
Slime
Sniffer
Spider
Squid
Stray
Sulfur_Cube
Tadpole
Turtle
Vex
Vindicator
Wandering_Trader
Warden
Witch
Wither
Wither_Skeleton
Zoglin
Zombie
Zombie_Pigman

--- Copper Golem ---
Copper_Golem
Exposed_Copper_Golem
Weathered_Copper_Golem
Oxidized_Copper_Golem

--- Creeper ---
Creeper
Charged_Creeper

--- Snow Golem ---
Snow_Golem
Derpy_Snow_Golem

--- Goat ---
Goat
Screaming_Goat

--- Strider ---
Hot_Strider
Cold_Strider

--- Nautilus ---
Nautilus
Temperate_Zombie_Nautilus
Warm_Zombie_Nautilus

--- Axolotl ---
Blue_Axolotl
Cyan_Axolotl
Gold_Axolotl
Lucy_Axolotl
Wild_Axolotl

--- Cat ---
Cat_AllBack
Cat_Black
Cat_British_ShortHair
Cat_Calico
Cat_Jellie
Cat_Persian
Cat_Ragdoll
Cat_Red
Cat_Siamese
Cat_Tabby
Cat_White

--- Chicken ---
Chicken
Temperate_Chicken
Cold_Chicken
Warm_Chicken

--- Cow ---
Cow
Temperate_Cow
Cold_Cow
Warm_Cow

--- Fox ---
Fox_Normal
Fox_Snow

--- Frog ---
Frog
Temperate_Frog
Cold_Frog
Warm_Frog

--- Horse ---
Black_Horse
Brown_Horse
Chestnut_Horse
Creamy_Horse
Dark_Brown_Horse
Gray_Horse
White_Horse
Skeleton_Horse
Zombie_Horse

--- Llama ---
Brown_Llama
Creamy_Llama
Gray_Llama
White_Llama
Brown_Llama_Trader
Creamy_Llama_Trader
Gray_Llama_Trader
White_Llama_Trader

--- Panda ---
Panda_Normal
Panda_Aggressive
Panda_Lazy
Panda_Brown
Panda_Worried
Panda_Playful
Panda_Weak

--- Pig ---
Pig
Temperate_Pig
Cold_Pig
Warm_Pig

--- Rabbit ---
Black_Rabbit
Black_and_White_Rabbit
Brown_Rabbit
Gold_Rabbit
Salt_and_Pepper_Rabbit
The_Killer_Bunny
Toast_Rabbit
White_Rabbit

--- Sheep ---
Black_Sheep
Blue_Sheep
Brown_Sheep
Cyan_Sheep
Gray_Sheep
Green_Sheep
Light_Blue_Sheep
Lime_Sheep
Magenta_Sheep
Orange_Sheep
Pink_Sheep
Purple_Sheep
Rainbow_Sheep
Red_Sheep
Light_Gray_Sheep
White_Sheep
Yellow_Sheep

--- Villager ---
Villager
Villager_Armorer
Villager_Butcher
Villager_Cartographer
Villager_Cleric
Villager_Farmer
Villager_Fisherman
Villager_Fletcher
Villager_Leatherworker
Villager_Librarian
Villager_Mason
Villager_Nitwit
Villager_Shepherd
Villager_Toolsmith
Villager_Weaponsmith

--- Zombie Villager ---
Zombie_Villager
Zombie_Armorer_Villager
Zombie_Butcher_Villager
Zombie_Cartographer_Villager
Zombie_Cleric_Villager
Zombie_Farmer_Villager
Zombie_Fisherman_Villager
Zombie_Fletcher_Villager
Zombie_Leatherworker_Villager
Zombie_Librarian_Villager
Zombie_Mason_Villager
Zombie_Nitwit_Villager
Zombie_Shepherd_Villager
Zombie_Toolsmith_Villager
Zombie_Weaponsmith_Villager

--- Wolf ---
Wild_Wolf
Tamed_Wolf
Wild_Ashen_Wolf
Wild_Black_Wolf
Wild_Chestnut_Wolf
Wild_Pale_Wolf
Wild_Rusty_Wolf
Wild_Snowy_Wolf
Wild_Spotted_Wolf
Wild_Striped_Wolf
Wild_Woods_Wolf
Tamed_Ashen_Wolf
Tamed_Black_Wolf
Tamed_Chestnut_Wolf
Tamed_Pale_Wolf
Tamed_Rusty_Wolf
Tamed_Snowy_Wolf
Tamed_Spotted_Wolf
Tamed_Striped_Wolf
Tamed_Woods_Wolf

--- Parrot ---
Blue_Parrot
Cyan_Parrot
Gray_Parrot
Green_Parrot
Red_Parrot

--- Fish (fishing drops) ---
Tropical_Fish
Cod
Salmon
Pufferfish
```

</details>

---

## Credits

- [CyberDrain](https://www.spigotmc.org/resources/ultimate-mob-heads-fork.70019/) — original plugin
- fahlur — intermediate fork
- Lord-Lofi — this fork (Paper 1.21.11 support, new mobs, advancement system, variant-aware drops)
