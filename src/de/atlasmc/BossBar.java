package de.atlasmc;

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
		
		public static BarColor getByID(int id) {
			BarColor[] values = values();
			return values[id];
		}
	}
	
	public static enum BarStyle {
		NO_DIVISION,
		SEGMENTED_6,
		SEGMENTED_10,
		SEGMENTED_12,
		SEGMENTED_20;
		
		public static BarStyle getByID(int id) {
			BarStyle[] values = values();
			return values[id];
		}
	}
	
	public static enum BarFlag {
		DARKEN_SKY,
		PLAY_BOSS_MUSIC,
		CREATE_FOG
	}
}
