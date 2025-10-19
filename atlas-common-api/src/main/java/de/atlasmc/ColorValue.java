package de.atlasmc;

import de.atlasmc.chat.ChatColor;

public interface ColorValue {
	
	Color asColor();
	
	ChatColor asChatColor();
	
	String asConsoleColor();
	
	int asRGB();
	
	int asARGB();

}
