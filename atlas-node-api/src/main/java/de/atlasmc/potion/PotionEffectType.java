package de.atlasmc.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;

@RegistryHolder(key = "atlas:potion_effect_type", target = Target.PROTOCOL)
public abstract class PotionEffectType extends ProtocolRegistryValueBase  {
	
	private static final ProtocolRegistry<PotionEffectType> REGISTRY;

	public static final NamespacedKey
	ABSORPTION = NamespacedKey.literal("minecraft:absorption"),
	BAD_OMEN = NamespacedKey.literal("minecraft:bad_omen"),
	BLINDNESS = NamespacedKey.literal("minecraft:blindness"),
	CONDUIT_POWER = NamespacedKey.literal("minecraft:conduit_power"),
	DARKNESS = NamespacedKey.literal("minecraft:darkness"),
	DOLPHINS_GRACE = NamespacedKey.literal("minecraft:dolphins_grace"),
	FIRE_RESISTANCE = NamespacedKey.literal("minecraft:fire_resistance"),
	GLOWING = NamespacedKey.literal("minecraft:glowing"),
	HASTE = NamespacedKey.literal("minecraft:haste"),
	HEALTH_BOOST = NamespacedKey.literal("minecraft:health_boost"),
	HERO_OF_THE_VILLAGE = NamespacedKey.literal("minecraft:hero_of_the_village"),
	HUNGER = NamespacedKey.literal("minecraft:hunger"),
	INFESTED = NamespacedKey.literal("minecraft:infested"),
	INSTANT_DAMAGE = NamespacedKey.literal("minecraft:instant_damage"),
	INSTANT_HEALTH = NamespacedKey.literal("minecraft:instant_health"),
	INVISIBILITY = NamespacedKey.literal("minecraft:invisibility"),
	JUMP_BOOST = NamespacedKey.literal("minecraft:jump_boost"),
	LEVITATION = NamespacedKey.literal("minecraft:levitation"),
	LUCK = NamespacedKey.literal("minecraft:luck"),
	MINING_FATIGUE = NamespacedKey.literal("minecraft:mining_fatigue"),
	NAUSEA = NamespacedKey.literal("minecraft:nausea"),
	NIGHT_VISION = NamespacedKey.literal("minecraft:night_vision"),
	OOZING = NamespacedKey.literal("minecraft:oozing"),
	POISON = NamespacedKey.literal("minecraft:poison"),
	RAID_OMEN = NamespacedKey.literal("minecraft:raid_omen"),
	REGENERATION = NamespacedKey.literal("minecraft:regeneration"),
	RESISTANCE = NamespacedKey.literal("minecraft:resistance"),
	SATURATION = NamespacedKey.literal("minecraft:saturation"),
	SLOWNESS = NamespacedKey.literal("minecraft:slowness"),
	SLOW_FALLING = NamespacedKey.literal("minecraft:slow_falling"),
	SPEED = NamespacedKey.literal("minecraft:speed"),
	STRENGTH = NamespacedKey.literal("minecraft:strength"),
	TRIAL_OMEN = NamespacedKey.literal("minecraft:trial_omen"),
	UNLUCK = NamespacedKey.literal("minecraft:unluck"),
	WATER_BREATHING = NamespacedKey.literal("minecraft:water_breathing"),
	WEAKNESS = NamespacedKey.literal("minecraft:weakness"),
	WEAVING = NamespacedKey.literal("minecraft:weaving"),
	WIND_CHARGED = NamespacedKey.literal("minecraft:wind_charged"),
	WITHER = NamespacedKey.literal("minecraft:wither");
	
	static {
		REGISTRY = Registries.createRegistry(PotionEffectType.class);
	}
	
	private final int color;
	
	public PotionEffectType(NamespacedKey key, int id, int color) {
		super(key, id);
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
