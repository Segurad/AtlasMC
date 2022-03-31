package de.atlasmc.chat.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;

public interface ChatComponent {
	
	public static final String 
		FONT_DEFAULT = "minecraft:default",
		FONT_ALT = "minecraft:alt",
		FONT_UNIFORM = "minecraft:uniform";
	
	public boolean isBold();
	
	public void setBold(boolean bold);
	
	public boolean isItalic();
	
	public void setItalic(boolean italic);
	
	public boolean isUnderlined();
	
	public void setUnderlined(boolean underlined);
	
	public boolean isObfuscated();
	
	public void setObfuscated(boolean obfuscated);
	
	public boolean isStrikethrough();
	
	public void setStrikethrough(boolean strikethrough);
	
	public String getFont();
	
	public void setFont(String font);
	
	/**
	 * Returns the RGB value of the color used or -1.
	 * If set by {@link #setColor(ChatColor)} it will return the index+1 as negative
	 * @return rgb or enum -(index+1) or -1
	 */
	public int getColor();
	
	/**
	 * Returns the ChatColor represented by the color or null
	 * @return ChatColor or null
	 */
	public ChatColor getColorChat();
	
	/**
	 * Sets the color as RGB value or -1 to remove or -({@link ChatColor#getID()}+1) for chat color
	 * @param rgb
	 */
	public void setColor(int rgb);
	
	/**
	 * Sets the color by {@link ChatColor} or null to remove.
	 * Format is allowed but the dedicated methods should be used instead
	 * @param color
	 */
	public void setColor(ChatColor color);
	
	/**
	 * Returns whether or not this Component has a color
	 * @return true if has color
	 */
	public boolean hasColor();
	
	/**
	 * Returns whether or not this Component has a {@link ChatColor}
	 * @return true if has {@link ChatColor}
	 */
	public boolean hasChatColor();
	
	public ClickEvent getClickEvent();
	
	public void setClickEvent(ClickEvent event);
	
	public HoverEvent getHoverEvent();
	
	public void setHoverEvent(HoverEvent event);
	
	public Chat toChat();
	
}
