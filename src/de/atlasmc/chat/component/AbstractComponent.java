package de.atlasmc.chat.component;

import de.atlasmc.chat.ChatColor;

public abstract class AbstractComponent implements ChatComponent {
	
	private byte flags; // 0x01 = bold, 0x02 = italic, 0x04 = underlined, 0x08 = obfuscated, 0x10 = strikethrough
	private String font = ChatComponent.FONT_DEFAULT;
	private int color = -1;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	
	@Override
	public boolean isBold() {
		return (flags & 0x01) == 0x01;
	}

	@Override
	public void setBold(boolean bold) {
		flags = (byte) (bold ? flags | 0x01 : flags & 0xFE);
	}

	@Override
	public boolean isItalic() {
		return (flags & 0x02) == 0x02;
	}

	@Override
	public void setItalic(boolean italic) {
		flags = (byte) (italic ? flags | 0x02 : flags & 0xFD);
	}

	@Override
	public boolean isUnderlined() {
		return (flags & 0x04) == 0x04;
	}

	@Override
	public void setUnderlined(boolean underlined) {
		flags = (byte) (underlined ? flags | 0x04 : flags & 0xFB);
	}

	@Override
	public boolean isObfuscated() {
		return (flags & 0x08) == 0x08;
	}

	@Override
	public void setObfuscated(boolean obfuscated) {
		flags = (byte) (obfuscated ? flags | 0x08 : flags & 0xF7);
	}

	@Override
	public String getFont() {
		return font;
	}

	@Override
	public void setFont(String font) {
		this.font = font;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public ChatColor getColorChat() {
		return color < -1 ? ChatColor.getByID(-(color+1)) : null;
	}

	@Override
	public void setColor(int rgb) {
		color = rgb;
	}

	@Override
	public void setColor(ChatColor color) {
		this.color = color == null ? -1 : -(color.getID()+1);
	}

	@Override
	public boolean isStrikethrough() {
		return (flags & 0x10) == 0x10;
	}

	@Override
	public void setStrikethrough(boolean strikethrough) {
		flags = (byte) (strikethrough ? flags | 0x10 : flags & 0xEF);
	}

	@Override
	public ClickEvent getClickEvent() {
		return clickEvent;
	}
	
	@Override
	public void setClickEvent(ClickEvent event) {
		this.clickEvent = event;
	}
	
	@Override
	public HoverEvent getHoverEvent() {
		return hoverEvent;
	}
	
	@Override
	public void setHoverEvent(HoverEvent event) {
		this.hoverEvent = event;
	}
	
	@Override
	public boolean hasChatColor() {
		return color < -1;
	}
	
	@Override
	public boolean hasColor() {
		return color != -1;
	}
	
}
