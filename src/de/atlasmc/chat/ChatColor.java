package de.atlasmc.chat;

import java.util.List;

import de.atlasmc.Color;

public enum ChatColor {

	BLACK('0', new Color(0x000000)),
	DARK_BLUE('1', new Color(0x0000AA)),
	DARK_GREEN('2', new Color(0x00AA00)),
	DARK_AQUA('3', new Color(0x00AAAA)),
	DARK_RED('4', new Color(0xAA0000)),
	DARK_PURPLE('5', new Color(0xAA00AA)),
	GOLD('6', new Color(0xFFAA00)),
	GRAY('7', new Color(0xAAAAAA)),
	DARK_GRAY('8', new Color(0x555555)),
	BLUE('9', new Color(0x5555FF)),
	GREEN('a', new Color(0x55FF55)),
	AQUA('b', new Color(0x55FFFF)),
	RED('c', new Color(0xFF5555)),
	LIGHT_PURPLE('d', new Color(0xFF55FF)),
	YELLOW('e', new Color(0xFFFF55)),
	WHITE('f', new Color(0xFFFFFF)),
	OBFUSCATED('k'),
	BOLD('l'),
	STRIKETHROUGH('m'),
	UNDERLINE('n'),
	ITALIC('o'),
	RESET('r');
	
	private static List<ChatColor> VALUES;

	private final char formatID;
	private final Color color;
	
	private ChatColor(char formatID) {
		this(formatID, Color.BLACK);
	}
	
	private ChatColor(char formatID, Color color) {
		this.formatID = formatID;
		this.color = color;
	}
	
	/**
	 * Returns the Color of this ChatColor<br>
	 * {@link #OBFUSCATED}, {@link #BOLD}, {@link #STRIKETHROUGH}, 
	 * {@link #UNDERLINE}, {@link #ITALIC} and {@link #RESET}
	 * will return black 
	 * @return the color of this ChatColor
	 */
	public Color getColor() {
		return color;
	}
	
	public char getFormatID() {
		return formatID;
	}
	
	public int getID() {
		return ordinal();
	}

	@Override
	public String toString() {
		return "§" + formatID;
	}

	public static ChatColor getByFormatID(char id) {
		for (ChatColor c : getValues()) {
			if (c.getFormatID() == id) return c;
		}
		return null;
	}
	
	public static ChatColor getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ChatColor> getValues() {
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
