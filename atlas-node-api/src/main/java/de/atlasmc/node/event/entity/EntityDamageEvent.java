package de.atlasmc.node.event.entity;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.entity.DamageType;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

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
	
	public static class DamageReduction implements NBTSerializable {
		
		public static final NBTCodec<DamageReduction>
		NBT_HANDLER = NBTCodec
						.builder(DamageReduction.class)
						.defaultConstructor(DamageReduction::new)
						.setRedirectAfterConstruction(false)
						.dataSetField("type", DamageReduction::getTypes, DamageReduction::setTypes, Registries.getRegistry(DamageType.class))
						.floatField("base", DamageReduction::getBase, DamageReduction::setBase, 0)
						.floatField("factor", DamageReduction::getFactor, DamageReduction::setFactor, 0)
						.floatField("horizontal_blocking_angle", DamageReduction::getBlockAngle, DamageReduction::setBlockAngle, 90)
						.build();
		
		private DataSet<DamageType> types;
		private float base;
		private float factor;
		private float blockAngle = 90;
		
		public DataSet<DamageType> getTypes() {
			return types;
		}
		
		public void setTypes(DataSet<DamageType> types) {
			this.types = types;
		}
		
		public float getBase() {
			return base;
		}
		
		public void setBase(float base) {
			this.base = base;
		}
		
		public float getFactor() {
			return factor;
		}
		
		public void setFactor(float factor) {
			this.factor = factor;
		}
		
		public float getBlockAngle() {
			return blockAngle;
		}
		
		public void setBlockAngle(float blockAngle) {
			this.blockAngle = blockAngle;
		}

		@Override
		public NBTCodec<? extends DamageReduction> getNBTCodec() {
			return NBT_HANDLER;
		}
		
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
