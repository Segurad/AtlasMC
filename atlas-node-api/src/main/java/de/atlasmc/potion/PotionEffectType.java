package de.atlasmc.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key = "atlas:potion_effect_type", target = Target.PROTOCOL)
public abstract class PotionEffectType extends ProtocolRegistryValueBase  {
	
	public static final RegistryKey<PotionEffectType> REGISTRY_KEY = Registries.getRegistryKey(PotionEffectType.class);

	public static final RegistryValueKey<PotionEffectType>
	ABSORPTION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:absorption")),
	BAD_OMEN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bad_omen")),
	BLINDNESS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:blindness")),
	CONDUIT_POWER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:conduit_power")),
	DARKNESS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:darkness")),
	DOLPHINS_GRACE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:dolphins_grace")),
	FIRE_RESISTANCE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:fire_resistance")),
	GLOWING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:glowing")),
	HASTE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:haste")),
	HEALTH_BOOST = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:health_boost")),
	HERO_OF_THE_VILLAGE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:hero_of_the_village")),
	HUNGER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:hunger")),
	INFESTED = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:infested")),
	INSTANT_DAMAGE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:instant_damage")),
	INSTANT_HEALTH = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:instant_health")),
	INVISIBILITY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:invisibility")),
	JUMP_BOOST = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:jump_boost")),
	LEVITATION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:levitation")),
	LUCK = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:luck")),
	MINING_FATIGUE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:mining_fatigue")),
	NAUSEA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:nausea")),
	NIGHT_VISION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:night_vision")),
	OOZING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:oozing")),
	POISON = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:poison")),
	RAID_OMEN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:raid_omen")),
	REGENERATION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:regeneration")),
	RESISTANCE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:resistance")),
	SATURATION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:saturation")),
	SLOWNESS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:slowness")),
	SLOW_FALLING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:slow_falling")),
	SPEED = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:speed")),
	STRENGTH = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:strength")),
	TRIAL_OMEN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:trial_omen")),
	UNLUCK = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:unluck")),
	WATER_BREATHING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:water_breathing")),
	WEAKNESS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:weakness")),
	WEAVING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:weaving")),
	WIND_CHARGED = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wind_charged")),
	WITHER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wither"));
	
	private final int color;
	
	public PotionEffectType(NamespacedKey key, int id, int color) {
		super(key, id);
		this.color = color;
	}
	
	public PotionEffectType(ConfigurationSection cfg) {
		super(cfg);
		this.color = cfg.getInt("color");
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
	
	public PotionEffect createEffect() {
		return createEffect(0, 1);
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public final int getID() {
		return id;
	}
	
	public static PotionEffectType get(CharSequence key) {
		return REGISTRY_KEY.getValue(key);
	}

	public static PotionEffectType getByID(int id) {
		return getRegistry().getByID(id);
	}
	
	public static ProtocolRegistry<PotionEffectType> getRegistry() {
		return REGISTRY_KEY.getRegistry();
	}
	
}
