# Slayer Plugin - Official Continuation

[![SpigotMC](https://img.shields.io/badge/SpigotMC-Slayer-orange)](https://www.spigotmc.org/resources/slayer-mythic-mobs-support.102769/) 
[![Modrinth](https://img.shields.io/badge/Modrinth-Slayer-green)](https://modrinth.com/plugin/slayer)
[![License](https://img.shields.io/github/license/TheNewEconomy/slayer)](LICENSE)
[![Discord](https://img.shields.io/discord/WNdwzpy?logo=discord)](https://discord.gg/WNdwzpy)
[![GitHub](https://img.shields.io/badge/GitHub-Slayer-blue)](https://github.com/TheNewEconomy/Slayer/tree/main)

## Overview

This is the official continuation of the **Slayer** plugin, originally developed by **underscore95**, later maintained by **creatorfromhell**. Slayer is a powerful and highly configurable mob-killing tracker with deep **MythicMobs** integration, allowing servers to create engaging and rewarding mob-hunting experiences.

## Features

- 🏹 **Kill Tracking** - Track player kills for custom mobs, MythicMobs, and vanilla entities.
- 🎯 **Custom Slayer Tasks** - Define custom mob-kill objectives with configurable rewards.
- 🏆 **Player Progression** - Add challenges that encourage players to hunt specific mobs.
- ⚙️ **MythicMobs Support** - Seamless compatibility with MythicMobs, allowing unique boss-killing missions.
- 💾 **YAML Storage** - Stores player progress and configuration in YAML format.
- 🛠 **Highly Configurable** - Modify messages, tasks, rewards, and more with an intuitive config.
- 🌎 **PlaceholderAPI Support** - Integrate Slayer progress into scoreboards, holograms, and chat.

## Installation

1. Download the latest version from [SpigotMC](https://www.spigotmc.org/resources/slayer-mythic-mobs-support.102769/) or [Modrinth](https://modrinth.com/plugin/slayer).
2. Place the `.jar` file, Slayer in your server's `/plugins/` directory.
3. Restart or reload your server to generate the default configuration files.
4. Modify settings in `config.yml` and define mob types in `mob-types.yml`.
5. (Optional) Integrate with **MythicMobs**, **PlaceholderAPI**, and configure storage settings if needed.

## Configuration

Slayer uses `config.yml` to manage its settings, including defining slayer tasks, rewards, and general settings. The `mob-types.yml` file specifies different mob categories.

### `config.yml`

```yaml
allow-spawners: false
save-timer: 120
action-bar-enabled: false
cancel-task: true

# Slayer Tasks
 tasks:
   0:
      mob-type: 0
      kills: 13
      name: Zombie Subjugation
      description:
       - "&7The local zombie population is out of control."
      reward: 11
```

### `mob-types.yml`

```yaml
mob-types:
   0:
      name: Zombie
      entity: ZOMBIE
      type: VANILLA
      material: ROTTEN_FLESH
```

## Commands & Permissions

| Command              | Permission            | Description                         |
|----------------------|----------------------|-------------------------------------|
| `/slayer`           | `slayer.open`        | Opens the Slayer menu.             |
| `/canceltask`       | `slayer.canceltask`  | Cancels a player's task.           |
| `/collectrewards`   | `slayer.collectreward` | Collect rewards remotely.          |
| `/slayerbuy`        | `slayer.buy`         | Buy items remotely.                |
| `/starttask`        | `slayer.startask`    | Start a Slayer task.               |
| `/givepoints`       | `slayer.givepoints`  | Give Slayer points to someone.     |
| `/reloadslayer`     | `slayer.reload`      | Reload Slayer configs.             |

## Dependencies (Optional)

- [MythicMobs](https://www.spigotmc.org/resources/mythicmobs.5702/) - Enables the creation of custom mob objectives.
- [Vault](https://www.spigotmc.org/resources/vault.34315/) - Required for economy-based rewards.
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) - Allows integration of Slayer progress into scoreboards, holograms, and chat.

## Support

For support, updates, and feature requests, join our **Discord** community: [Join Here](https://discord.gg/WNdwzpy)

## License

This project is licensed under the [GPLv3 License](LICENSE).

## Contributors

Special thanks to **underscore95** for their hard work starting Slayer, and their original ideas bringing it to life in the first place.

For more details, visit the [Slayer GitHub repository](https://github.com/TheNewEconomy/Slayer/tree/main).

---

### 🎮 Keep the hunt alive with Slayer! Happy mob hunting!

