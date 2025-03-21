package de.atlasmc.chat.component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.io.SNBTWriter;

public abstract class AbstractBaseComponent<T extends AbstractBaseComponent<T>> implements ChatComponent {
	
	public static final CharKey
	JSON_BOLD = CharKey.literal("bold"),
	JSON_ITALIC = CharKey.literal("italic"),
	JSON_UNDERLINED = CharKey.literal("underlined"),
	JSON_OBFUSCATED = CharKey.literal("obfuscated"),
	JSON_STRIKETHROUGH = CharKey.literal("strikethrough"),
	JSON_COLOR = CharKey.literal("color"),
	JSON_FONT = CharKey.literal("font"),
	JSON_CLICK_EVENT = CharKey.literal("clickEvent"),
	JSON_HOVER_EVENT = CharKey.literal("hoverEvent"),
	JSON_EXTRA = CharKey.literal("extra"),
	JSON_INSERTION = CharKey.literal("insertion"),
	JSON_ACTION = CharKey.literal("action"), // for hover and click event
	JSON_CONTENTS = CharKey.literal("contents"), // hover event
	JSON_VALUE = CharKey.literal("value"), // click event
	JSON_TYPE = CharKey.literal("type");
	
	private byte flags; // 0x01 = bold, 0x02 = italic, 0x04 = underlined, 0x08 = obfuscated, 0x10 = strikethrough
	private String font = ChatComponent.FONT_DEFAULT;
	private int color = -1;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private List<ChatComponent> extra;
	private String insertion;
	
	protected abstract String getType();
	
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
		return color < -1 ? ChatColor.getByColor(color) : null;
	}

	@Override
	public T setColor(int rgb) {
		color = rgb;
		return getThis();
	}

	@Override
	public T setColor(ChatColor color) {
		this.color = color == null ? -1 : color.getColor().asRGB();
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
	public String toJsonText() {
		StringWriter w = new StringWriter();
		SNBTWriter writer = new SNBTWriter(w);
		try {
			writer.writeCompoundTag();
			addContents(writer);
			writer.writeEndTag();
		} catch (IOException e) {
			throw new NBTException("Error while writing component!", e);
		}
		return w.toString();
	}
	
	@Override
	public void toJson(CharSequence key, NBTWriter writer) throws IOException {
		writer.writeCompoundTag(key);
		addContents(writer);
		writer.writeEndTag();
	}
	
	@Override
	public String toLegacyText() {
		return ChatUtil.componentToLegacy(this);
	}
	
	@Override
	public String toString() {
		return toJsonText();
	}
	
	/**
	 * Write the contents of this component
	 * @param buff
	 */
	@Override
	public void addContents(final NBTWriter writer) throws IOException {
		String type = getType();
		if (type != null)
			writer.writeStringTag(JSON_TYPE, type);
		if (isBold())
			writer.writeByteTag(JSON_BOLD, true);
		if (isItalic())
			writer.writeByteTag(JSON_ITALIC, true);
		if (isUnderlined())
			writer.writeByteTag(JSON_UNDERLINED, true);
		if (isObfuscated())
			writer.writeByteTag(JSON_OBFUSCATED, true);
		if (isStrikethrough())
			writer.writeByteTag(JSON_STRIKETHROUGH, true);
		if (hasColor()) {
			if (hasChatColor())
				writer.writeStringTag(JSON_COLOR, getColorChat().getName());
			else
				writer.writeStringTag(JSON_COLOR, "#" + Integer.toHexString(color));
		}
		if (insertion != null)
			writer.writeStringTag(JSON_INSERTION, insertion);
		if (!getFont().equals(ChatComponent.FONT_DEFAULT))
			writer.writeStringTag(JSON_FONT, getFont());
		final ClickEvent clickEvent = getClickEvent();
		if (clickEvent != null) {
			writer.writeCompoundTag(JSON_CLICK_EVENT);
			writer.writeStringTag(JSON_ACTION, clickEvent.getAction().getName());
			writer.writeStringTag(JSON_VALUE, clickEvent.getValue());
			writer.writeEndTag();
		}
		final HoverEvent hoverEvent = getHoverEvent();
		if (hoverEvent != null) {
			writer.writeCompoundTag(JSON_HOVER_EVENT);
			writer.writeStringTag(JSON_ACTION, hoverEvent.getAction().getName());
			writer.writeCompoundTag(JSON_CONTENTS);
			hoverEvent.addContents(writer);
			writer.writeEndTag();
			writer.writeEndTag();
		}
		if (hasExtra()) {
			final List<ChatComponent> extra = getExtra();
			writer.writeListTag(JSON_EXTRA, TagType.COMPOUND, extra.size());
			for (ChatComponent comp : extra) {
				writer.writeCompoundTag();
				comp.addContents(writer);
				writer.writeEndTag();
			}
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
	public String toText() {
		return toJsonText();
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
	public String toRawText() {
		return ChatUtil.componentToRawText(this);
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
		if (this.extra != null) {
			copy.extra = new ArrayList<>(this.extra.size());
			for (ChatComponent extra : this.extra)
				copy.extra.add(extra.clone());
		}
		return copy.getThis();
	}
	
	@Override
	public ChatComponent toComponent() {
		return this;
	}
	
	protected abstract T getThis();
	
}
