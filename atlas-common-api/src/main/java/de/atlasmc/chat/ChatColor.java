package de.atlasmc.chat;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum ChatColor implements EnumName, EnumID, EnumValueCache {

	BLACK('0', Color.fromRGB(0x000000)),
	DARK_BLUE('1', Color.fromRGB(0x0000AA)),
	DARK_GREEN('2', Color.fromRGB(0x00AA00)),
	DARK_AQUA('3', Color.fromRGB(0x00AAAA)),
	DARK_RED('4', Color.fromRGB(0xAA0000)),
	DARK_PURPLE('5', Color.fromRGB(0xAA00AA)),
	GOLD('6', Color.fromRGB(0xFFAA00)),
	GRAY('7', Color.fromRGB(0xAAAAAA)),
	DARK_GRAY('8', Color.fromRGB(0x555555)),
	BLUE('9', Color.fromRGB(0x5555FF)),
	GREEN('a', Color.fromRGB(0x55FF55)),
	AQUA('b', Color.fromRGB(0x55FFFF)),
	RED('c', Color.fromRGB(0xFF5555)),
	LIGHT_PURPLE('d', Color.fromRGB(0xFF55FF)),
	YELLOW('e', Color.fromRGB(0xFFFF55)),
	WHITE('f', Color.fromRGB(0xFFFFFF)),
	OBFUSCATED('k', ""), // TODO research obfuscated console format
	BOLD('l', "\033[1m"),
	STRIKETHROUGH('m', ""), // TODO research strike through console format
	UNDERLINE('n', "\033[4m"),
	ITALIC('o', ""), // TODO research italic console format
	RESET('r', "\033[0m");
	
	private static List<ChatColor> VALUES;

	private final char formatID;
	private final Color color;
	private final String consoleFormat;
	private final String name;
	
	private ChatColor(char formatID, String console) {
		this(formatID, Color.BLACK, console);
	}
	
	private ChatColor(char formatID, Color color) {
		this(formatID, color, color.asConsoleColor());
	}
	
	private ChatColor(char formatID, Color color, String console) {
		this.formatID = formatID;
		this.color = color;
		this.consoleFormat = console;
		this.name = this.name().toLowerCase().intern();
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
	
	public String getConsoleFormat() {
		return consoleFormat;
	}
	
	public int getID() {
		return ordinal();
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "§" + formatID;
	}
	
	/**
	 * Modifies the {@link ChatComponent} with this ChatColor
	 * @param component
	 */
	public void modify(ChatComponent component) {
		switch(this) {
		case BOLD:
			component.setBold(true);
			break;
		case ITALIC:
			component.setItalic(true);
			break;
		case OBFUSCATED:
			component.setObfuscated(true);
			break;
		case STRIKETHROUGH:
			component.setStrikethrough(true);
			break;
		case UNDERLINE:
			component.setUnderlined(true);
			break;
		default:
			component.setColor(this);
			break;
		}
	}

	public static ChatColor getByFormatID(char id) {
		for (ChatColor c : getValues()) {
			if (c.getFormatID() == id) 
				return c;
		}
		return null;
	}
	
	public static ChatColor getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<ChatColor> colors = getValues();
		final int size = colors.size();
		for (int i = 0; i < size; i++) {
			ChatColor color = colors.get(i);
			if (color.name.equals(name))
				return color;
		}
		return null;
	}
	
	public static ChatColor getByID(int id) {
		return getValues().get(id);
	}
	
	public static ChatColor getByColor(Color color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		List<ChatColor> colors = getValues();
		final int size = colors.size();
		for (int i = 0; i < size; i++) {
			ChatColor c = colors.get(i);
			if (c.color.equals(color))
				return c;
		}
		return null;
	}
	
	public static ChatColor getByColor(int rgb) {
		List<ChatColor> colors = getValues();
		final int size = colors.size();
		for (int i = 0; i < size; i++) {
			ChatColor c = colors.get(i);
			if (c.color.asRGB() == rgb)
				return c;
		}
		return null;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ChatColor> getValues() {
		if (VALUES == null) {
			synchronized (ChatColor.class) {
				if (VALUES == null)
					VALUES = List.of(values());
			}
		}
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
}
