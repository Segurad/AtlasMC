package de.atlasmc.event.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.entity.EntityDamageEvent.DamageCause;

/**
 * The event should be called by a InstantRespawn class
 * The event is not handled by UnionCore 
 * @author Segurad
 *
 */
public class PlayerInstantRespawnEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private final Player player;
	private final Entity killer;
	private final DamageCause cause;
	private String deathMessage;
	
	public PlayerInstantRespawnEvent(Player player, Entity killer, DamageCause cause, String deathMessage) {
		this.player = player;
		this.killer = killer;
		this.cause = cause;
		this.deathMessage = deathMessage;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean value) {
		cancelled = value;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Entity getKiller() {
		return killer;
	}
	
	public DamageCause getCause() {
		return cause;
	}
	
	public String getDeathMessage() {
		return deathMessage;
	}
	
	public void setDeathMessage(String msg) {
		deathMessage = msg;
	}

}
