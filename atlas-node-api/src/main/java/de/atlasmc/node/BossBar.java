package de.atlasmc.node;

import de.atlasmc.IDHolder;

public class BossBar {
	
	private String title;
	private float health;
	private BarColor color;
	private BarStyle style;
	private int flags;
	
	public BossBar(String title, float health, BarColor color, BarStyle style) {
		this.title = title;
		this.health = health;
		this.color = color;
		this.style = style;
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

	/**
	 * Sets sets the {@link BarFlag}s for a {@link BossBar}
	 * <ul>
	 * <li>0x01 {@link BarFlag#DARKEN_SKY}</li>
	 * <li>0x02 {@link BarFlag#PLAY_BOSS_MUSIC}</li>
	 * <li>0x04 {@link BarFlag#CREATE_FOG}</li>
	 * </ul>
	 */
	public int getFlags() {
		return flags;
	}
	
	public boolean hasFlag(BarFlag flag) {
		return (flags & flag.mask) != 0;
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

	public void setFlag(BarFlag flag, boolean set) {
		if (set) {
			this.flags |= flag.mask;
		} else {
			this.flags &= ~flag.mask;
		}
	}

	public static enum BarColor implements IDHolder {
		
		PINK,
		BLUE,
		RED,
		GREEN,
		YELLOW,
		PURPLE,
		WHITE;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
	public static enum BarStyle implements IDHolder {
		
		NO_DIVISION,
		SEGMENTED_6,
		SEGMENTED_10,
		SEGMENTED_12,
		SEGMENTED_20;
		
		@Override
		public int getID() {
			return ordinal();
		}

	}
	
	public static enum BarFlag {
		
		DARKEN_SKY(0x1),
		PLAY_BOSS_MUSIC(0x2),
		CREATE_FOG(0x4);
		
		public final int mask;
		
		private BarFlag(int mask) {
			this.mask = mask;
		}
		
	}
}
