package de.atlasmc;

import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.ItemStack;

public enum Particle {
	
	AMBIENT_ENTITY_EFFECT(0),
	ANGRY_VILLAGER(1),
	BARRIER(2),
	BLOCK(3, BlockData.class),
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
	EFFECT(15),
	ELDER_GUARDIAN(16),
	ENCHANTED_HIT(17),
	END_ROD(19),
	ENTITY_EFFECT(20),
	EXPLSION_EMITTER(21),
	EXPLOSION(22),
	FALLING_DUST(23, BlockData.class),
	FIREWORK(24),
	FISHING(25),
	FLAME(26),
	HAPPY_VILLAGER(28),
	COMPOSTER(29),
	HEART(30),
	INSTANT_EFFECT(31),
	ITEM(32, ItemStack.class),
	ITEM_SLIME(33),
	ITEM_SNOWBALL(34),
	LARGE_SMOKE(35),
	LAVA(36),
	MYCELIUM(37),
	NOTE(38),
	POOF(39),
	PORTAL(40),
	RAIN(41),
	SMOKE(42),
	SNEEZE(43),
	SPIT(44),
	SQUID_INK(45),
	SWEEP_ATTACK(46),
	TOTEM_OF_UNDYING(47),
	UNDERWATER(48),
	SPLASH(49),
	WITCH(50),
	BUBBLE_POP(51),
	CURRENT_COWN(52),
	BUBBLE_COLUMN_UP(53),
	NAUTILUS(54),
	DOLPHIN(55),
	CAMPFIRE_COSY(56),
	CAMPFIRE_SINGLE_SMOKE(57),
	DIRPPING_HONEY(58),
	FALLING_HONEY(59),
	LANDING_HONEY(60),
	FALLING_NECTAR(61);
	
	private static List<Particle> VALUES;
	
	private final int id;
	private final Class<?> data;
	
	private Particle(int id, Class<?> data) {
		this.id = id;
		this.data = data;
	}
	
	private Particle(int id) {
		this.id = id;
		this.data = void.class;
	}
	
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
			if (color == null) throw new IllegalArgumentException("Color can not be null!");
			if (scale < 0.01 || scale > 4) throw new IllegalArgumentException("Size is not between 0.01 and 4: " + scale);
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
		if (this.data == null) return data == null;
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
	
}
