package de.atlasmc.event.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class EntityDamageEvent extends EntityEvent implements Cancellable {
	
	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private boolean cancelled;
	private DamageCause cause;
	private DamageModifier mod;
	private double damage;
	
	public EntityDamageEvent(Entity damagee, DamageCause cause, double damage) {
		this(damagee, cause, cause.getDamageModifier(), damage);
	}
	
	public EntityDamageEvent(Entity damagee, DamageCause cause, DamageModifier mod, double damage) {
		super(damagee);
		this.cause = cause;
		this.mod = mod;
		this.damage = damage;
	}
	
	@FunctionalInterface
	public static interface DamageModifier {
		
		double calcFinalDamage(Entity damager, Entity damagee, double damage);
	
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
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public DamageCause getCause() {
		return cause;
	}
	
	public DamageModifier getDamageModifier() {
		return mod;
	}
	
	public double getFinalDamage() {
		return mod != null ? mod.calcFinalDamage(null, getEntity(), damage) : damage;
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
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
