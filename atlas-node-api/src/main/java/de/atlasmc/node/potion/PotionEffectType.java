package de.atlasmc.node.potion;

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
import static de.atlasmc.registry.RegistryValueKey.ofLiteral;

@RegistryHolder(key = "atlas:potion_effect_type", target = Target.PROTOCOL)
public abstract class PotionEffectType extends ProtocolRegistryValueBase  {
	
	public static final RegistryKey<PotionEffectType> REGISTRY_KEY = Registries.getRegistryKey(PotionEffectType.class);

	public static final RegistryValueKey<PotionEffectType>
	ABSORPTION = ofLiteral(REGISTRY_KEY, "minecraft:absorption"),
	BAD_OMEN = ofLiteral(REGISTRY_KEY, "minecraft:bad_omen"),
	BLINDNESS = ofLiteral(REGISTRY_KEY, "minecraft:blindness"),
	CONDUIT_POWER = ofLiteral(REGISTRY_KEY, "minecraft:conduit_power"),
	DARKNESS = ofLiteral(REGISTRY_KEY, "minecraft:darkness"),
	DOLPHINS_GRACE = ofLiteral(REGISTRY_KEY, "minecraft:dolphins_grace"),
	FIRE_RESISTANCE = ofLiteral(REGISTRY_KEY, "minecraft:fire_resistance"),
	GLOWING = ofLiteral(REGISTRY_KEY, "minecraft:glowing"),
	HASTE = ofLiteral(REGISTRY_KEY, "minecraft:haste"),
	HEALTH_BOOST = ofLiteral(REGISTRY_KEY, "minecraft:health_boost"),
	HERO_OF_THE_VILLAGE = ofLiteral(REGISTRY_KEY, "minecraft:hero_of_the_village"),
	HUNGER = ofLiteral(REGISTRY_KEY, "minecraft:hunger"),
	INFESTED = ofLiteral(REGISTRY_KEY, "minecraft:infested"),
	INSTANT_DAMAGE = ofLiteral(REGISTRY_KEY, "minecraft:instant_damage"),
	INSTANT_HEALTH = ofLiteral(REGISTRY_KEY, "minecraft:instant_health"),
	INVISIBILITY = ofLiteral(REGISTRY_KEY, "minecraft:invisibility"),
	JUMP_BOOST = ofLiteral(REGISTRY_KEY, "minecraft:jump_boost"),
	LEVITATION = ofLiteral(REGISTRY_KEY, "minecraft:levitation"),
	LUCK = ofLiteral(REGISTRY_KEY, "minecraft:luck"),
	MINING_FATIGUE = ofLiteral(REGISTRY_KEY, "minecraft:mining_fatigue"),
	NAUSEA = ofLiteral(REGISTRY_KEY, "minecraft:nausea"),
	NIGHT_VISION = ofLiteral(REGISTRY_KEY, "minecraft:night_vision"),
	OOZING = ofLiteral(REGISTRY_KEY, "minecraft:oozing"),
	POISON = ofLiteral(REGISTRY_KEY, "minecraft:poison"),
	RAID_OMEN = ofLiteral(REGISTRY_KEY, "minecraft:raid_omen"),
	REGENERATION = ofLiteral(REGISTRY_KEY, "minecraft:regeneration"),
	RESISTANCE = ofLiteral(REGISTRY_KEY, "minecraft:resistance"),
	SATURATION = ofLiteral(REGISTRY_KEY, "minecraft:saturation"),
	SLOWNESS = ofLiteral(REGISTRY_KEY, "minecraft:slowness"),
	SLOW_FALLING = ofLiteral(REGISTRY_KEY, "minecraft:slow_falling"),
	SPEED = ofLiteral(REGISTRY_KEY, "minecraft:speed"),
	STRENGTH = ofLiteral(REGISTRY_KEY, "minecraft:strength"),
	TRIAL_OMEN = ofLiteral(REGISTRY_KEY, "minecraft:trial_omen"),
	UNLUCK = ofLiteral(REGISTRY_KEY, "minecraft:unluck"),
	WATER_BREATHING = ofLiteral(REGISTRY_KEY, "minecraft:water_breathing"),
	WEAKNESS = ofLiteral(REGISTRY_KEY, "minecraft:weakness"),
	WEAVING = ofLiteral(REGISTRY_KEY, "minecraft:weaving"),
	WIND_CHARGED = ofLiteral(REGISTRY_KEY, "minecraft:wind_charged"),
	WITHER = ofLiteral(REGISTRY_KEY, "minecraft:wither");
	
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
