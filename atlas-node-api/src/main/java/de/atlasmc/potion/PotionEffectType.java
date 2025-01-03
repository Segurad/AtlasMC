package de.atlasmc.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;

@RegistryHolder(key = "atlas:potion_effect_type", target = Target.PROTOCOL)
public abstract class PotionEffectType implements Namespaced {
	
	public static final ProtocolRegistry<PotionEffectType> REGISTRY;

	public static final NamespacedKey
	SPEED = NamespacedKey.literal("minecraft:speed"),
	SLOWNESS = NamespacedKey.literal("minecraft:slowness"),
	HASTE = NamespacedKey.literal("minecraft:haste"),
	MINING_FATIGUE = NamespacedKey.literal("minecraft:mining_fatigue"),
	STRENGTH = NamespacedKey.literal("minecraft:strength"),
	INSTANT_HEALTH = NamespacedKey.literal("minecraft:instant_health"),
	INSTANT_DAMAGE = NamespacedKey.literal("minecraft:instant_damage"),
	JUMP_BOOST = NamespacedKey.literal("minecraft:jump_boost"),
	NAUSEA = NamespacedKey.literal("minecraft:nausea"),
	REGENERATION = NamespacedKey.literal("minecraft:regeneration"),
	RESISTANCE = NamespacedKey.literal("minecraft:resistance"),
	FIRE_RESISTANCE = NamespacedKey.literal("minecraft:fire_resistance"),
	WATER_BREATHING = NamespacedKey.literal("minecraft:water_breathing"),
	INVISIBILITY = NamespacedKey.literal("minecraft:invisibility"),
	BLINDNESS = NamespacedKey.literal("minecraft:blindness"),
	NIGHT_VISION = NamespacedKey.literal("minecraft:night_vision"),
	HUNGER = NamespacedKey.literal("minecraft:hunger"),
	WEAKNESS = NamespacedKey.literal("minecraft:weakness"),
	POISON = NamespacedKey.literal("minecraft:poison"),
	WITHER = NamespacedKey.literal("minecraft:wither"),
	HEALTH_BOOST = NamespacedKey.literal("minecraft:health_boost"),
	ABSORPTION = NamespacedKey.literal("minecraft:absorption"),
	SATURATION = NamespacedKey.literal("minecraft:saturation"),
	GLOWING = NamespacedKey.literal("minecraft:glowing"),
	LEVITATION = NamespacedKey.literal("minecraft:levitation"),
	LUCK = NamespacedKey.literal("minecraft:luck"),
	UNLUCK = NamespacedKey.literal("minecraft:unluck"),
	SLOW_FALLING = NamespacedKey.literal("minecraft:slow_falling"),
	CONDUIT_POWER = NamespacedKey.literal("minecraft:conduit_power"),
	DOLPHINS_GRACE = NamespacedKey.literal("minecraft:dolphins_grace"),
	BAD_OMEN = NamespacedKey.literal("minecraft:bad_omen"),
	HERO_OF_THE_VILLAGE = NamespacedKey.literal("minecraft:hero_of_the_village"),
	DARKNESS = NamespacedKey.literal("minecraft:darkness"),
	TRIAL_OMEN = NamespacedKey.literal("minecraft:trial_omen"),
	RAID_OMEN = NamespacedKey.literal("minecraft:raid_omen"),
	WIND_CHARGED = NamespacedKey.literal("minecraft:wind_charged"),
	WEAVING = NamespacedKey.literal("minecraft:weaving"),
	OOZING = NamespacedKey.literal("minecraft:oozing"),
	INFESTED = NamespacedKey.literal("minecraft:infested");
	
	static {
		REGISTRY = Registries.createRegistry(PotionEffectType.class);
		REGISTRY.setIDSupplier(PotionEffectType::getID);
	}
	
	private final NamespacedKey key;
	private final int id;
	private final int color;
	
	public PotionEffectType(NamespacedKey key, int id, int color) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
		this.id = id;
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public PotionEffect createEffect(int amplifier, int duration) {
		return createEffect(amplifier, duration, false, true, true, null);
	}
	
	public PotionEffect createEffect(int amplifier, int duration, UUID uuid) {
		return createEffect(amplifier, duration, false, true, true, uuid);
	}
	
	/**
	 * Creates a {@link PotionEffect} of this type
	 * @param amplifier the level of the effect
	 * @param duration the time in ticks of the effect -1 for infinite
	 * @param reducedAmbient
	 * @param particles
	 * @param icon
	 * @return effect
	 */
	public PotionEffect createEffect(int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		return createEffect(amplifier, duration, reducedAmbient, particles, icon, null);
	}
	
	public abstract PotionEffect createEffect(int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid);
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public final int getID() {
		return id;
	}
	
	public static PotionEffectType get(NamespacedKey key) {
		return REGISTRY.get(key);
	}
	
	public static PotionEffectType get(String key) {
		return REGISTRY.get(key);
	}

	public static PotionEffectType getByID(int id) {
		return REGISTRY.getByID(id);
	}
	
}
