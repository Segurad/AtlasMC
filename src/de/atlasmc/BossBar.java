package de.atlasmc;

public class BossBar {
	
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
