package me.unfear.Slayer.listeners;

import me.unfear.Slayer.Language;
import me.unfear.Slayer.Main;
import me.unfear.Slayer.PlayerData;
import me.unfear.Slayer.SlayerLoader;
import me.unfear.Slayer.mobtypes.MobType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class EntityDeathListener implements Listener {
    private final Language language;
    private final SlayerLoader config;

    public EntityDeathListener(final Language language, final SlayerLoader config) {
        this.language = language;
        this.config = config;
    }

    private void sendActionBar(final Player player, final PlayerData data) {
        if (!config.isActionBarEnabled()) return;
        final int kills = data.getKills();
        final int required = data.getCurrentTask().getKills();
        if (required < kills) return;
        final String text = language.actionBar(kills, required);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));

    }

    @EventHandler
    void onDeath(final EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null || e.getEntity() instanceof Player) return;

        final Player player = e.getEntity().getKiller();

        final Entity entity = e.getEntity();
        final boolean spawner = entity.getPersistentDataContainer().getOrDefault(Main.inst.entityKey(), PersistentDataType.BOOLEAN, false);

        final SlayerLoader loader = Main.inst.getSlayerLoader();
        if (spawner && !loader.isAllowSpawners()) return;

        final PlayerData data = loader.getPlayerData(player.getUniqueId());

        for (final MobType mobType : Main.inst.getMobTypeLoader().getMobTypes()) {
            if (!mobType.isThis(entity)) continue;
            data.incrementEntityKills(mobType.getId());
            break;
        }

        if (data.getCurrentTask() == null || !data.getCurrentTask().getMobType().isThis(entity)) return;
        data.setKills(data.getKills() + 1);
        sendActionBar(player, data);
    }
}
