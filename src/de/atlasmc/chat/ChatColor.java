package de.atlasmc.chat;

public enum ChatColor {

	BLACK,
	DARK_BLUE,
	DARK_GREEN,
	DARK_CYAN,
	DARK_RED,
	PURPLE,
	GOLD,
	GRAY,
	DARK_GRAY,
	BLUE,
	GREEN,
	CYAN,
	RED,
	PINK,
	YELLOW,
	WHITE,
	OBFUSCATED,
	BOLD,
	STRIKETHROUGH,
	UNDERLINE,
	ITALIC,
	RESET;
	
	public static String translateColor(String value) {
		return translateAlternateColorCodes('&', value);
	}

	public static String translateAlternateColorCodes(char prefix, String value) {
		return null;
	}

	public int getID() {
		return ordinal();
	}
	
	public static ChatColor getByID(int id) {
		return values()[id];
	}
	
}
