package de.atlasmc.chat;

public enum ChatColor {

	GREEN, WHITE;
	
	public static String translateColor(String value) {
		return translateAlternateColorCodes('&', value);
	}

	public static String translateAlternateColorCodes(char prefix, String value) {
		return null;
	}
	
}
