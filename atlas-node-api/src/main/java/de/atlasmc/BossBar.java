package de.atlasmc;

import java.util.List;

public class BossBar {
	
	private String title;
	private float health;
	private BarColor color;
	private BarStyle style;
	private BarFlag flags;
	
	public BossBar(String title, float health, BarColor color, BarStyle style, BarFlag flag) {
		this.title = title;
		this.health = health;
		this.color = color;
		this.style = style;
		this.flags = flag;
	}
	
	public String getTitle() {
		return title;
	}

	public float getHealth() {
		return health;
	}

	public BarColor getColor() {
		return color;
	}

	public BarStyle getStyle() {
		return style;
	}

	public BarFlag getFlags() {
		return flags;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setColor(BarColor color) {
		this.color = color;
	}

	public void setStyle(BarStyle style) {
		this.style = style;
	}

	public void setFlags(BarFlag flags) {
		this.flags = flags;
	}

	public static enum BarColor {
		PINK,
		BLUE,
		RED,
		GREEN,
		YELLOW,
		PURPLE,
		WHITE;
		
		private static List<BarColor> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static BarColor getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<BarColor> getValues() {
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
	
	public static enum BarStyle {
		NO_DIVISION,
		SEGMENTED_6,
		SEGMENTED_10,
		SEGMENTED_12,
		SEGMENTED_20;
		
		private static List<BarStyle> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static BarStyle getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<BarStyle> getValues() {
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
	
	public static enum BarFlag {
		DARKEN_SKY,
		PLAY_BOSS_MUSIC,
		CREATE_FOG
	}
}
