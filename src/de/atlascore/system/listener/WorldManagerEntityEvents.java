package de.atlascore.system.listener;

import de.atlasmc.entity.Player;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.Listener;
import de.atlasmc.event.entity.EntityDamageEvent;
import de.atlasmc.event.entity.EntityDamageEvent.DamageCause;
import de.atlasmc.world.World;
import de.atlasmc.world.WorldFlag;

final class WorldManagerEntityEvents implements Listener {

	public WorldManagerEntityEvents() {}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamge(EntityDamageEvent e) {
		World data = e.getEntity().getWorld();
		if (data == null)
			return;
		if (e.getCause() == DamageCause.FALL) {
			if (data.hasFlag(WorldFlag.DISABLE_DAMAGE_BY_FALL))
				e.setCancelled(true);
		} else if (e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK) {
			if (data.hasFlag(WorldFlag.DISABLE_DAMAGE_BY_FIRE))
				e.setCancelled(true);
		} else if (e.getCause() == DamageCause.DROWNING) {
			if (data.hasFlag(WorldFlag.DISABLE_DAMAGE_BY_DROWNING))
				e.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onDamage(EntityDamageByBlockEvent e) {
		World data = e.getDamager().getWorld();
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_DAMAGE_BY_BLOCK))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onDamage(EntityDamageByEntityEvent e) {
		World data = e.getEntity().getWorld();
		if (data == null)
			return;
		if (e.getDamager() instanceof Player) {
			if (e.getEntity() instanceof Player) {
				if (data.hasFlag(WorldFlag.DISABLE_DAMAGE_BY_PLAYER))
					e.setCancelled(true);
			} else if (data.hasFlag(WorldFlag.DISABLE_DAMAGE_TO_ENTITY))
				e.setCancelled(true);
		} else if (data.hasFlag(WorldFlag.DISABLE_DAMAGE_BY_ENTITY))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onFood(FoodLevelChangeEvent e) {
		World data = e.getEntity().getWorld();
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_HUNGER))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onSpawn(EntitySpawnEvent e) {
		World data = e.getLocation().getWorld();
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_MOBSPAWN))
			e.setCancelled(true);
	}
}
