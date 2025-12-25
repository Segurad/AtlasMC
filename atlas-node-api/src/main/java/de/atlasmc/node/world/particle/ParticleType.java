package de.atlasmc.node.world.particle;

import java.util.Objects;
import java.util.function.Function;
import de.atlasmc.IDHolder;
import de.atlasmc.util.enums.EnumName;

public enum ParticleType implements EnumName, IDHolder {
	
	ANGRY_VILLAGER,
	BLOCK(BlockDataParticle::new),
	BLOCK_MARKER(BlockDataParticle::new),
	BUBBLE,
	CLOUD,
	CRIT,
	DAMAGE_INDICATOR,
	DRAGON_BREATH(DragonBreathParticle::new),
	DRIPPING_LAVA,
	FALLING_LAVA,
	LANDING_LAVA,
	DRIPPING_WATER,
	FALLING_WATER,
	DUST(DustColorParticle::new),
	DUST_COLOR_TRANSITION(DustColorTransitionParticle::new),
	EFFECT(EffectParticle::new),
	ELDER_GUARDIAN,
	ENCHANTED_HIT,
	ENCHANT,
	END_ROD,
	ENTITY_EFFECT(ColorParticle::new),
	EXPLOSION_EMITTER,
	EXPLOSION,
	GUST,
	SMALL_GUST,
	GUST_EMITTER_LARGE,
	GUST_EMITTER_SMALL,
	SONIC_BOOM,
	FALLING_DUST(BlockDataParticle::new),
	FIREWORK,
	FISHING,
	FLAME,
	INFESTED,
	CHERRY_LEAVES,
	PALE_OAK_LEAVES,
	SCULK_SOUL,
	SCULK_CHARGE(SculkChargeParticle::new),
	SCULK_CHARGE_POP,
	SOUL_FIRE_FLAME,
	SOUL,
	FLASH,
	HAPPY_VILLAGER,
	COMPOSTER,
	HEART,
	INSTANT_EFFECT(EffectParticle::new),
	ITEM(ItemParticle::new),
	VIBRATION(VibrationParticle::new),
	TRAIL(TrailParticle::new),
	ITEM_SLIME,
	ITEM_COBWEB,
	ITEM_SNOWBALL,
	LARGE_SMOKE,
	LAVA,
	MYCELIUM,
	NOTE,
	POOF,
	PORTAL,
	RAIN,
	SMOKE,
	WHITE_SMOKE,
	SNEEZE,
	SPIT,
	SQUID_INK,
	SWEEP_ATTACK,
	TOTEM_OF_UNDYING,
	UNDERWATER,
	SPLASH,
	WITCH,
	BUBBLE_POP,
	CURRENT_DOWN,
	BUBBLE_COLUMN_UP,
	NAUTILUS,
	DOLPHIN,
	CAMPFIRE_COSY_SMOKE,
	CAMPFIRE_SIGNAL_SMOKE,
	DRIPPING_HONEY,
	FALLING_HONEY,
	LANDING_HONEY,
	FALLING_NECTAR,
	FALLING_SPORE_BLOSSOM,
	ASH,
	CRIMSON_SPORE,
	WARPED_SPORE,
	SPORE_BLOSSOM_AIR,
	DRIPPING_OBSIDIAN_TEAR,
	FALLING_OBSIDIAN_TEAR,
	LANDING_OBSIDIAN_TEAR,
	REVERSE_PORTAL,
	WHITE_ASH,
	SMALL_FLAME,
	SNOWFLAKE,
	DRIPPING_DRIPSTONE_LAVA,
	FALLING_DRIPSTONE_LAVA,
	DRIPPING_DRIPSTONE_WATER,
	FALLING_DRIPSTONE_WATER,
	GLOW_SQUID_INK,
	GLOW,
	WAX_ON,
	WAX_OFF,
	ELECTRIC_SPARK,
	SCRAPE,
	SHRIEK(DelayedParticle::new),
	EGG_CRACK,
	DUST_PLUME,
	TRIAL_SPAWNER_DETECTION,
	TRIAL_SPAWNER_DETECTION_OMINOUS,
	VAULT_CONNECTION,
	DUST_PILLAR(BlockDataParticle::new),
	OMINOUS_SPAWNING,
	RAID_OMEN,
	TRIAL_OMEN,
	BLOCK_CRUMBLE;

	private final Function<ParticleType, Particle> constructor;
	private final String name;
	
	private ParticleType(Function<ParticleType, Particle> constructor) {
		this.name = "minecraft:" + name().toLowerCase().intern();
		this.constructor = Objects.requireNonNull(constructor);
	}
	
	private ParticleType() {
		name = "minecraft:" + name().toLowerCase().intern();
		final var instance = new SimpleParticle(this);
		constructor = (_) -> { return instance; }; 
	}
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	public Particle createParticle() {
		return constructor.apply(this);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
