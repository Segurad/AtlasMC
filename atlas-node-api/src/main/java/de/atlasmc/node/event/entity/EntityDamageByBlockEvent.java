package de.atlasmc.node.event.entity;

import de.atlasmc.node.block.Block;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.event.ServerHandlerList;

public class EntityDamageByBlockEvent extends EntityDamageEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private Block damager;
	
	public EntityDamageByBlockEvent(Block damager, Entity damagee,  DamageCause cause, DamageModifier mod, double damage) {
		super(damagee, cause, mod, damage);
		this.damager = damager;
	}
	
	public Block getDamager() {
		return damager;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
