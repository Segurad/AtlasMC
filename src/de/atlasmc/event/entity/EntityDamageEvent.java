package de.atlasmc.event.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.event.AbstractServerEvent;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class EntityDamageEvent extends AbstractServerEvent implements Cancellable {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	private boolean cancelled;
	private Entity entity;
	private DamageCause cause;
	private DamageModifier mod;
	private double damage;
	
	public EntityDamageEvent(Entity damagee, DamageCause cause, double damage) {
		this(damagee, cause, cause.getDamageModifier(), damage);
	}
	
	public EntityDamageEvent(Entity damagee, DamageCause cause, DamageModifier mod, double damage) {
		super(damagee.getServer());
		this.entity = damagee;
		this.cause = cause;
		this.mod = mod;
		this.damage = damage;
	}
	
	public static abstract class DamageModifier {
		public static DamageModifier PHYSICAL, TRUE, MAGIC;
		
		public abstract double calcDamage(Entity damager, Entity damagee, double damage);
		public abstract double calcFinalDamage(Entity damager, Entity damagee, double damage);
	}
	
	public static class DamageCause {
		public static DamageCause BLOCK_EXPLOSION,
		CONTACT,
		CRAMMING,
		DRAGON_BREATH,
		DROWNING,
		DRYOUT,
		ENTITY_ATTACK,
		ENTITY_SWEEP_ATTACK,
		FALL,
		FALLING_BLOCK,
		FIRE,
		FIRE_TICK,
		FLY_INTO_WALL,
		HOT_FLOOR,
		LAVA,
		LIGHTNING,
		MAGIC,
		MELTING,
		POISON,
		PROJECTILE,
		STARVATION,
		SUFFOCATION,
		SUICIDE,
		THORNS,
		VOID,
		WITHER;
		
		private DamageModifier mod;
		
		public DamageCause(DamageModifier mod) {
			this.mod = mod;
		}
		
		public DamageModifier getDamageModifier() {
			return mod;
		}
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public DamageCause getCause() {
		return cause;
	}
	
	public DamageModifier getDamageModifier() {
		return mod;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
