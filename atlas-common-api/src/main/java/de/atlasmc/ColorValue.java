package de.atlasmc;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface ColorValue {
	
	/**
	 * Returns the color value as {@link Color}
	 * @return color
	 */
	@NotNull
	Color asColor();
	
	/**
	 * Returns the color value as {@link ChatColor} or null if no match
	 * @return color
	 */
	@Nullable
	ChatColor asChatColor();
	
	/**
	 * Returns the console format string
	 * @return value
	 */
	@NotNull
	String asConsoleColor();
	
	/**
	 * Returns this color value in RGB format
	 * @return value
	 */
	int asRGB();
	
	/**
	 * Returns this color value in ARGB format
	 * @return value
	 */
	int asARGB();

}
