package de.atlasmc.potion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.entity.Entity;

public abstract class PotionEffectType {

	public static PotionEffectType
	SPEED,
	SLOWNESS,
	HASTE,
	MINING_FATIGUE,
	STRENGTH,
	INSTANT_HEALTH,
	INSTANT_DAMAGE,
	JUMP_BOOST,
	NAUSEA,
	REGENERATION,
	RESISTANCE,
	FIRE_RESISTANCE,
	WATER_BREATHING,
	INVISIBILITY,
	BLINDNESS,
	NIGHT_VISION,
	HUNGER,
	WEAKNESS,
	POISON,
	WITHER,
	HEALTH_BOOST,
	ABSORPTION,
	SATURATION,
	GLOWING,
	LEVITATION,
	LUCK,
	UNLUCK,
	SLOW_FALLING,
	CONDUIT_POWER,
	DOLPHINS_GRACE,
	BAD_OMEN,
	HERO_OF_THE_VILLAGE;
	// DARKNESS; TODO 1.19
	
	private static Map<Integer, PotionEffectType> BY_ID;
	
	static {
		BY_ID = new ConcurrentHashMap<>();
	}
	
	private final int id;
	
	public PotionEffectType(int id) {
		this.id = id;
		BY_ID.put(id, this);
	}
	
	/**
	 * This method is called when the effect is added to an {@link Entity} and it returns whether it will stay at this entity or not
	 * @param entity
	 * @param amplifier
	 * @param duration
	 * @return
	 */
	public abstract boolean addEffect(Entity entity, int amplifier, int duration);
	
	public abstract void removeEffect(Entity entity, int amplifier, int duration);
	
	public abstract void tick(Entity entity, int amplifier, int duration);
	
	public abstract boolean isTicking();
	
	public final int getID() {
		return id;
	}

	public static PotionEffectType getByID(int id) {
		return BY_ID.get(id);
	}
	
}
