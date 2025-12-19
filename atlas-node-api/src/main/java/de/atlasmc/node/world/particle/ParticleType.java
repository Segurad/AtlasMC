package de.atlasmc.node.world.particle;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import de.atlasmc.Color;
import de.atlasmc.IDHolder;
import de.atlasmc.node.Location;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.enums.EnumName;

public enum ParticleType implements EnumName, IDHolder {
	
	ANGRY_VILLAGER,
	BLOCK(BlockDataParticle::new),
	BLOCK_MARKER(BlockDataParticle::new),
	BUBBLE,
	CLOUD,
	CRIT,
	DAMAGE_INDICATOR,
	DRAGON_BREATH,
	DRIPPING_LAVA,
	FALLING_LAVA,
	LANDING_LAVA,
	DRIPPING_WATER,
	FALLING_WATER,
	DUST(DustColorParticle::new),
	DUST_COLOR_TRANSITION(DustColorTransitionParticle::new),
	EFFECT,
	ELDER_GUARDIAN,
	ENCHANTED_HIT,
	ENCHANT,
	END_ROD,
	ENTITY_EFFECT(Color.class),
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
	SCULK_CHARGE(Float.class),
	SCULK_CHARGE_POP,
	SOUL_FIRE_FLAME,
	SOUL,
	FLASH,
	HAPPY_VILLAGER,
	COMPOSTER,
	HEART,
	INSTANT_EFFECT,
	ITEM(ItemStack.class),
	VIBRATION(VibrationData.class),
	TRAIL,
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
	/**
	 * Has a Integer value for delay
	 */
	SHRIEK(Integer.class),
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
	
	public static class VibrationData {
		
		private boolean entity;
		private Location sourceLocation;
		private int sourceEntity;
		private float eyeHeight;
		private int travelTicks;
		
		public VibrationData(Location sourceLocation, int travelTicks) {
			this.sourceLocation = sourceLocation;
			this.travelTicks = travelTicks;
		}
		
		public VibrationData(int entityID, float eyeHeight, int travelTicks) {
			entity = true;
			this.sourceEntity = entityID;
			this.eyeHeight = eyeHeight;
			this.travelTicks = travelTicks;
		}
		
		public boolean isEntity() {
			return entity;
		}
		
		public int getSourceEntity() {
			return sourceEntity;
		}
		
		public float getEyeHeight() {
			return eyeHeight;
		}
		
		public int getTravelTicks() {
			return travelTicks;
		}
		
		public Location getSourceLocation() {
			return sourceLocation;
		}
		
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
