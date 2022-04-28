package de.atlasmc.potion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	private final int color;
	
	public PotionEffectType(int id, int color) {
		this.id = id;
		this.color = color;
		BY_ID.put(id, this);
	}
	
	public int getColor() {
		return color;
	}
	
	public PotionEffect createEffect(int amplifier, int duration) {
		return createEffect(amplifier, duration);
	}
	
	public abstract PotionEffect createEffect(int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon);
	
	public final int getID() {
		return id;
	}

	public static PotionEffectType getByID(int id) {
		return BY_ID.get(id);
	}
	
}
