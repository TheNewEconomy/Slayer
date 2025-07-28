package me.unfear.Slayer.listeners;

import me.unfear.Slayer.Main;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerSpawnListener implements Listener {

	@EventHandler
	void onSpawn(final SpawnerSpawnEvent e) {
		final Entity entity = e.getEntity();
		entity.getPersistentDataContainer().set(Main.inst.entityKey(), PersistentDataType.BOOLEAN, true);
	}
}
