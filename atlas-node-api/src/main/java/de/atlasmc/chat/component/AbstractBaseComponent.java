package de.atlasmc.chat.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.util.JsonBuffer;

abstract class AbstractBaseComponent<T extends AbstractBaseComponent<T>> implements ChatComponent {
	
	public static final String
	JSON_BOLD = "bold",
	JSON_ITALIC = "italic",
	JSON_UNDERLINED = "underlined",
	JSON_OBFUSCATED = "obfuscated",
	JSON_STRIKETHROUGH = "strikethrough",
	JSON_COLOR = "color",
	JSON_FONT = "font",
	JSON_CLICK_EVENT = "clickEvent",
	JSON_HOVER_EVENT = "hoverEvent",
	JSON_EXTRA = "extra",
	JSON_INSERTION = "insertion",
	JSON_ACTION = "action", // for hover and click event
	JSON_CONTENTS = "contents", // hover event
	JSON_VALUE = "value"; // click event
	
	private byte flags; // 0x01 = bold, 0x02 = italic, 0x04 = underlined, 0x08 = obfuscated, 0x10 = strikethrough
	private String font = ChatComponent.FONT_DEFAULT;
	private int color = -1;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private List<ChatComponent> extra;
	private String insertion;
	
	@Override
	public String getInsertion() {
		return insertion;
	}
	
	@Override
	public T setInsertion(String insertion) {
		this.insertion = insertion;
		return getThis();
	}
	
	@Override
	public boolean isBold() {
		return (flags & 0x01) == 0x01;
	}

	@Override
	public T setBold(boolean bold) {
		flags = (byte) (bold ? flags | 0x01 : flags & 0xFE);
		return getThis();
	}

	@Override
	public boolean isItalic() {
		return (flags & 0x02) == 0x02;
	}

	@Override
	public T setItalic(boolean italic) {
		flags = (byte) (italic ? flags | 0x02 : flags & 0xFD);
		return getThis();
	}

	@Override
	public boolean isUnderlined() {
		return (flags & 0x04) == 0x04;
	}

	@Override
	public T setUnderlined(boolean underlined) {
		flags = (byte) (underlined ? flags | 0x04 : flags & 0xFB);
		return getThis();
	}

	@Override
	public boolean isObfuscated() {
		return (flags & 0x08) == 0x08;
	}

	@Override
	public T setObfuscated(boolean obfuscated) {
		flags = (byte) (obfuscated ? flags | 0x08 : flags & 0xF7);
		return getThis();
	}

	@Override
	public String getFont() {
		return font;
	}

	@Override
	public T setFont(String font) {
		if (font == null)
			font = ChatComponent.FONT_DEFAULT;
		this.font = font;
		return getThis();
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
	public T setColor(int rgb) {
		color = rgb;
		return getThis();
	}

	@Override
	public T color(ChatColor color) {
		this.color = color == null ? -1 : -(color.getID()+1);
		return getThis();
	}

	@Override
	public boolean isStrikethrough() {
		return (flags & 0x10) == 0x10;
	}

	@Override
	public T setStrikethrough(boolean strikethrough) {
		flags = (byte) (strikethrough ? flags | 0x10 : flags & 0xEF);
		return getThis();
	}

	@Override
	public ClickEvent getClickEvent() {
		return clickEvent;
	}
	
	@Override
	public T setClickEvent(ClickEvent event) {
		this.clickEvent = event;
		return getThis();
	}
	
	@Override
	public HoverEvent getHoverEvent() {
		return hoverEvent;
	}
	
	@Override
	public T setHoverEvent(HoverEvent event) {
		this.hoverEvent = event;
		return getThis();
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
	public String getJsonText() {
		JsonBuffer buff = new JsonBuffer();
		buff.beginObject(null);
		addContents(buff);
		buff.endObject();
		return buff.toString();
	}
	
	@Override
	public String getLegacyText() {
		return ChatUtil.legacyFromComponent(this);
	}
	
	@Override
	public String toString() {
		return getJsonText();
	}
	
	/**
	 * Write the contents of this component
	 * @param buff
	 */
	@Override
	public void addContents(final JsonBuffer buff) {
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
				buff.appendText(JSON_COLOR, getColorChat().getNameID());
			else
				buff.appendText(JSON_COLOR, "#" + Integer.toHexString(color));
		}
		if (insertion != null)
			buff.append(JSON_INSERTION, insertion);
		if (!getFont().equals(ChatComponent.FONT_DEFAULT))
			buff.appendText(JSON_FONT, getFont());
		final ClickEvent clickEvent = getClickEvent();
		if (clickEvent != null) {
			buff.beginObject(JSON_CLICK_EVENT)
				.append(JSON_ACTION, clickEvent.getAction().getName())
				.append(JSON_VALUE, clickEvent.getValue())
				.endObject();
		}
		final HoverEvent hoverEvent = getHoverEvent();
		if (hoverEvent != null) {
			buff.beginObject(JSON_HOVER_EVENT)
				.append(JSON_ACTION, hoverEvent.getAction().getName())
				.beginObject(JSON_CONTENTS);
				hoverEvent.addContents(buff);
			buff.endObject()
				.endObject();
		}
		if (hasExtra()) {
			final List<ChatComponent> extra = getExtra();
			buff.beginArray(JSON_EXTRA);
			for (ChatComponent comp : extra) {
				buff.beginObject(null);
				comp.addContents(buff);
				buff.endObject();
			}
			buff.endArray();
		}
	}
	
	@Override
	public List<ChatComponent> getExtra() {
		if (extra == null)
			extra = new ArrayList<>();
		return extra;
	}
	
	@Override
	public boolean hasExtra() {
		return extra != null && !extra.isEmpty();
	}
	
	@Override
	public T addExtra(ChatComponent component) {
		getExtra().add(component);
		return getThis();
	}
	
	@Override
	public ChatComponent extra(ChatComponent... components) {
		List<ChatComponent> extra = getExtra();
		for (ChatComponent comp : components) {
			extra.add(comp);
		}
		return this;
	}

	@Override
	public String getText() {
		return getJsonText();
	}

	@Override
	public T setExtra(Collection<ChatComponent> extra) {
		List<ChatComponent> list = getExtra();
		list.clear();
		list.addAll(extra);
		return getThis();
	}

	@Override
	public boolean hasLegacy() {
		return false;
	}

	@Override
	public boolean hasJson() {
		return true;
	}

	@Override
	public String getRawText() {
		return ChatUtil.rawTextFromComponent(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T clone() {
		AbstractBaseComponent<T> copy = null;
		try {
			copy = (AbstractBaseComponent<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
		copy.extra = new ArrayList<>(this.extra.size());
		for (ChatComponent extra : this.extra)
			copy.extra.add(extra.clone());
		return copy.getThis();
	}
	
	protected abstract T getThis();
	
}
