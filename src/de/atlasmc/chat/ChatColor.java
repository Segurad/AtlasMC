package de.atlasmc.chat;

public enum ChatColor {

	GREEN;
	
	public static String translateColor(String value) {
		return translateAlternateColorCodes('&', value);
	}

	public static String translateAlternateColorCodes(char c, String value) {
		return null;
	}
	
}
