package de.atlasmc.chat.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.util.JsonBuffer;

public class BaseComponent implements ChatComponent {
	
	protected static final String
	JSON_BOLD = "bold",
	JSON_ITALIC = "italic",
	JSON_UNDERLINED = "underlined",
	JSON_OBFUSCATED = "obfuscated",
	JSON_STRIKETHROUGH = "strikethrough",
	JSON_COLOR = "color",
	JSON_FONT = "font",
	JSON_CLICK_EVENT = "click_event",
	JSON_HOVER_EVENT = "hover_event",
	JSON_EXTRA = "extra";
	
	private byte flags; // 0x01 = bold, 0x02 = italic, 0x04 = underlined, 0x08 = obfuscated, 0x10 = strikethrough
	private String font = ChatComponent.FONT_DEFAULT;
	private int color = -1;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private List<ChatComponent> extra;
	
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
		if (font == null)
			font = ChatComponent.FONT_DEFAULT;
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
	
	@Override
	public String toJson() {
		JsonBuffer buff = new JsonBuffer();
		buff.beginObject(null);
		addContents(buff);
		buff.endObject();
		return buff.toString();
	}
	
	@Override
	public String toLegacy() {
		return null;
	}
	
	@Override
	public String toString() {
		return toJson();
	}
	
	/**
	 * Write the contents of this component
	 * @param buff
	 */
	public void addContents(JsonBuffer buff) {
		if (isBold())
			buff.appendBoolean(JSON_BOLD, true);
		if (isItalic())
			buff.appendBoolean(JSON_ITALIC, true);
		if (isUnderlined())
			buff.appendBoolean(JSON_UNDERLINED, true);
		if (isObfuscated())
			buff.appendBoolean(JSON_OBFUSCATED, true);
		if (isStrikethrough())
			buff.appendBoolean(JSON_STRIKETHROUGH, true);
		if (hasColor()) {
			if (hasChatColor())
				buff.appendText(JSON_COLOR, getColorChat().name().toLowerCase());
			else
				buff.appendText(JSON_COLOR, "#" + Integer.toString(color));
		}
		if (!getFont().equals(ChatComponent.FONT_DEFAULT))
			buff.appendText(JSON_FONT, getFont());
		if (getClickEvent() != null) {
			buff.beginObject(JSON_CLICK_EVENT);
			ClickEvent event = getClickEvent();
			buff.append(event.getAction().getName(), event.getValue());
			buff.endObject();
		}
		if (getHoverEvent() != null) {
			buff.beginObject(JSON_HOVER_EVENT);
			HoverEvent event = getHoverEvent();
			buff.append(event.getAction().getName(), event.getValue());
			buff.endObject();
		}
		if (hasExtra()) {
			List<ChatComponent> extra = getExtra();
			buff.beginArray(JSON_EXTRA);
			for (ChatComponent comp : extra) {
				buff.beginObject(null);
				comp.addContents(buff);
				buff.endObject();
			}
			buff.endArray();
		}
	}
	
	public List<ChatComponent> getExtra() {
		if (extra == null)
			extra = new ArrayList<>();
		return extra;
	}
	
	public boolean hasExtra() {
		return extra != null && !extra.isEmpty();
	}
	
	public void addExtra(ChatComponent component) {
		getExtra().add(component);
	}
	
}
