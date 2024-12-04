package de.atlasmc;

import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum Particle implements EnumName, EnumID, EnumValueCache {
	
	AMBIENT_ENTITY_EFFECT(0),
	ANGRY_VILLAGER(1),
	BLOCK(2, BlockData.class),
	BLOCK_MARKER(3),
	BUBBLE(4),
	CLOUD(5),
	CRIT(6),
	DAMAGE_INDICATOR(7),
	DRAGON_BREATH(8),
	DRIPPING_LAVA(9),
	FALLING_LAVA(10),
	LANDING_LAVA(11),
	DRIPPING_WATER(12),
	FALLING_WATER(13),
	DUST(14, DustOptions.class),
	DUST_COLOR_TRANSITION(15),
	EFFECT(16),
	ELDER_GUARDIAN(17),
	ENCHANTED_HIT(18),
	ENCHANT(19),
	END_ROD(20),
	ENTITY_EFFECT(21),
	EXPLOSION_EMITTER(22),
	EXPLOSION(23),
	SONIC_BOOM(24),
	FALLING_DUST(25, BlockData.class),
	FIREWORK(26),
	FISHING(27),
	FLAME(28),
	CHERRY_LEAVES(29),
	SCULK_SOUL(30),
	SCULK_CHARGE(31),
	SCULK_CHARGE_POP(32),
	SOUL_FIRE_FLAME(33),
	SOUL(34),
	FLASH(35),
	HAPPY_VILLAGER(36),
	COMPOSTER(37),
	HEART(38),
	INSTANT_EFFECT(39),
	ITEM(40, ItemStack.class),
	VIBRATION(41),
	ITEM_SLIME(42),
	ITEM_SNOWBALL(43),
	LARGE_SMOKE(44),
	LAVA(45),
	MYCELIUM(46),
	NOTE(47),
	POOF(48),
	PORTAL(49),
	RAIN(50),
	SMOKE(51),
	SNEEZE(52),
	SPIT(53),
	SQUID_INK(54),
	SWEEP_ATTACK(55),
	TOTEM_OF_UNDYING(56),
	UNDERWATER(57),
	SPLASH(58),
	WITCH(59),
	BUBBLE_POP(60),
	CURRENT_DOWN(61),
	BUBBLE_COLUMN_UP(62),
	NAUTILUS(63),
	DOLPHIN(64),
	CAMPFIRE_COSY_SMOKE(65),
	CAMPFIRE_SIGNAL_SMOKE(66),
	DRIPPING_HONEY(67),
	FALLING_HONEY(68),
	LANDING_HONEY(69),
	FALLING_NECTAR(70),
	FALLING_SPORE_BLOSSOM(71),
	ASH(72),
	CRIMSON_SPORE(73),
	WARPED_SPORE(74),
	SPORE_BLOSSOM_AIR(75),
	DRIPPING_OBSIDIAN_TEAR(76),
	FALLING_OBSIDIAN_TEAR(77),
	LANDING_OBSIDIAN_TEAR(78),
	REVERSE_PORTAL(79),
	WHITE_ASH(80),
	SMALL_FLAME(81),
	SNOWFLAKE(82),
	DRIPPING_DRIPSTONE_LAVA(83),
	FALLING_DRIPSTONE_LAVA(84),
	DRIPPING_DRIPSTONE_WATER(85),
	FALLING_DRIPSTONE_WATER(86),
	GLOW_SQUID_INK(87),
	GLOW(88),
	WAX_ON(89),
	WAX_OFF(90),
	ELECTRIC_SPARK(91),
	SCRAPE(92),
	SHRIEK(93),
	EGG_CRACK(94);
	
	private static List<Particle> VALUES;
	
	private final int id;
	private final Class<?> data;
	private final String name;
	
	private Particle(int id, Class<?> data) {
		this.id = id;
		this.data = data;
		String name = "minecraft:" + name().toLowerCase();
		this.name = name.intern();
	}
	
	private Particle(int id) {
		this(id, void.class);
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	public Class<?> getData() {
		return data;
	}
	
	public static class DustOptions {
		
		private final Color color;
		private final float scale;
		
		public DustOptions(Color color, float scale) {
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

	public static Particle getByID(int id) {
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
	public static List<Particle> getValues() {
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

	public static Particle getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<Particle> particles = getValues();
		final int size = particles.size();
		for (int i = 0; i < size; i++) {
			Particle particle = particles.get(i);
			if (particle.name.equals(name))
				return particle;
		}
		return null;
	}
	
}
