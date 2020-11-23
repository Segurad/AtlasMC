package de.atlasmc.event.listener;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.Player;
import de.atlasmc.util.Pair;

public class InstantRespawn { /*implements  Listener {
	
	public boolean allowFallDamage = true;
	public boolean allowFireDamage = true;
	public boolean allowLavaDamage = true;
	public boolean allowStarvingDamage = true;
	public boolean allowExplosionDamage = true;
	public long millisLastDMG = 3000;
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		final Player p = (Player) e.getEntity();
		final DamageCause cause = e.getCause();
		if (precheckDamage(p, e)) return;
		switch(cause) {
		case FALL: {
			if (!allowFallDamage) {
				e.setCancelled(true);
				return;
			} break; }
		case FIRE: {
			if (!allowFireDamage) {
				e.setCancelled(true);
				return;
			} break; }
		case FIRE_TICK: {
			if (!allowFireDamage) {
				e.setCancelled(true);
				return;
			} break; }
		case LAVA: {
			if (!allowLavaDamage) {
				e.setCancelled(true);
				return;
			} break; }
		case STARVATION: {
			if (!allowStarvingDamage) {
				e.setCancelled(true);
				return;
			} break; }
		case BLOCK_EXPLOSION: {
			if (!allowExplosionDamage) {
				e.setCancelled(true);
				return;
			} break; }
		case ENTITY_EXPLOSION: {
			if (!allowExplosionDamage) {
				e.setCancelled(true);
				return;
			} break; }
		default:
			break;
		}
		if ((p.getHealth() - e.getFinalDamage()) <= 0) {
			e.setCancelled(true);
			Pair<Long, Entity> pa = getLastDamage(p);
			if (pa != null) {
				Entity last = System.currentTimeMillis() - millisLastDMG > pa.getValue1() ? null : pa.getValue2();
				respawn(p, last, cause);
			} else respawn(p, null, cause);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		final Player p = (Player) e.getEntity();
		if (precheckDamage(p, e)) return;
		Entity damager = e.getDamager();
		final DamageCause cause = e.getCause();
		if (damager instanceof Projectile) {
			Projectile pro = (Projectile) e.getDamager();
			ProjectileSource source = pro.getShooter();
			if (source instanceof Entity) {
				damager = (Entity) source;
			} else damager = null;
		} else if (damager instanceof TNTPrimed) {
			TNTPrimed tnt = (TNTPrimed) damager;
			damager = tnt.getSource();
		}
		if (!damager.isValid()) damager = null;
 		if (checkDamage(p, damager, e)) return;
		if ((p.getHealth() - e.getFinalDamage()) <= 0) {
			e.setCancelled(true);
			respawn(p, damager, cause);
		} else setLastDamager(p, damager);
	}
	
	/**
	 * Override if needed
	 * @param player
	 * @return 
	 */
	protected Pair<Long, Entity> getLastDamage(Player player) {
		return null;
	}
	/**
	 * Override if needed
	 * @param player
	 * @param damager
	 */
	protected void setLastDamager(Player player, Entity damager) {}
	/**
	 * Override this method to modify the event at right before the health check
	 * @param player the affected Player
	 * @return true if you wish to cancel further checks 
	 * it is recommended to cancel the event in this case the returned value is true
	 */
	/*protected boolean precheckDamage(Player player, EntityDamageEvent event) {
		return false; 
	}
	/**
	 * Override this method to modify the event at right before the health check
	 * @param player the affected Player
	 * @param entity the attacker
	 * @return true if you wish to cancel further checks 
	 * it is recommended to cancel the event in this case the returned value is true
	 */
	/*protected boolean checkDamage(Player player, Entity entity, EntityDamageByEntityEvent event) {
		return false;
	}
	
	/**
	 * this method is called to create the PlayerInstantRespawnEvent
	 * @param player the affected Player
	 * @param killer the killer entity
	 * @param cause the death cause
	 */
	/*protected void respawn(Player player, Entity killer, DamageCause cause) {
		Bukkit.getPluginManager().callEvent(new PlayerInstantRespawnEvent(player, killer, cause, null));
	}*/
}
