package de.atlasmc.node.world.particle;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.node.SimpleLocation;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum ParticleType implements EnumName, EnumID, EnumValueCache {
	
	ANGRY_VILLAGER,
	BLOCK(BlockData.class),
	BLOCK_MARKER(BlockData.class),
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
	DUST(DustColor.class),
	DUST_COLOR_TRANSITION(DustColorTransition.class),
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
	FALLING_DUST(BlockData.class),
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
	DUST_PILLAR(BlockData.class),
	OMINOUS_SPAWNING,
	RAID_OMEN,
	TRIAL_OMEN,
	BLOCK_CRUMBLE;
	
	private static List<ParticleType> VALUES;
	
	private final Class<?> data;
	private final String name;
	
	private ParticleType(Class<?> data) {
		this.data = data;
		String name = "minecraft:" + name().toLowerCase();
		this.name = name.intern();
	}
	
	private ParticleType() {
		this(void.class);
	}
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	public Class<?> getData() {
		return data;
	}
	
	public static class VibrationData {
		
		private boolean entity;
		private SimpleLocation sourceLocation;
		private int sourceEntity;
		private float eyeHeight;
		private int travelTicks;
		
		public VibrationData(SimpleLocation sourceLocation, int travelTicks) {
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
		
		public SimpleLocation getSourceLocation() {
			return sourceLocation;
		}
		
	}
	
	public static class DustColor {
		
		private final Color color;
		private final float scale;
		
		public DustColor(Color color, float scale) {
			if (color == null) 
				throw new IllegalArgumentException("Color can not be null!");
			if (scale < 0.01 || scale > 4) 
				throw new IllegalArgumentException("Size is not between 0.01 and 4: " + scale);
			this.color = color;
			this.scale = scale;
		}
		
		public Color getColor() {
			return color;
		}
		
		public float getScale() {
			return scale;
		}
	}
	
	public static class DustColorTransition {
		
		private final Color from;
		private final Color to;
		private final float scale;
		
		public DustColorTransition(Color from, Color to, float scale) {
			if (from == null) 
				throw new IllegalArgumentException("From can not be null!");
			if (to == null)
				throw new IllegalArgumentException("To can not be null!");
			if (scale < 0.01 || scale > 4) 
				throw new IllegalArgumentException("Size is not between 0.01 and 4: " + scale);
			this.from = from;
			this.to = to;
			this.scale = scale;
		}
		
		public Color getColorFrom() {
			return from;
		}
		
		public Color getColorTo() {
			return to;
		}
		
		public float getScale() {
			return scale;
		}
	}

	public static ParticleType getByID(int id) {
		return getValues().get(id);
	}

	public boolean isValid(Object data) {
		if (this.data == null) 
			return data == null;
		return this.data.isInstance(data);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ParticleType> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public static ParticleType getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<ParticleType> particles = getValues();
		final int size = particles.size();
		for (int i = 0; i < size; i++) {
			ParticleType particle = particles.get(i);
			if (particle.name.equals(name))
				return particle;
		}
		return null;
	}
	
}
