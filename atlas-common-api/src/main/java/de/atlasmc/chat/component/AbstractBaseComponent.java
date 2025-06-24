package de.atlasmc.chat.component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.event.click.ClickEvent;
import de.atlasmc.chat.component.event.hover.HoverEvent;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.io.SNBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public abstract class AbstractBaseComponent<T extends AbstractBaseComponent<T>> implements ChatComponent {
	
	private byte flags; // 0x01 = bold, 0x02 = italic, 0x04 = underlined, 0x08 = obfuscated, 0x10 = strikethrough
	private String font = ChatComponent.FONT_DEFAULT;
	private Color color = null;
	private ChatColor chatColor = null;
	private Color shadowColor = null;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private List<ChatComponent> extra;
	private String insertion;
	
	@Override
	public Color getShadowColor() {
		return shadowColor;
	}
	
	@Override
	public void setShadowColor(Color color) {
		this.shadowColor = color;
	}
	
	@Override
	public boolean hasShadowColor() {
		return shadowColor != null;
	}
	
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
	public Color getColor() {
		return color;
	}

	@Override
	public ChatColor getColorChat() {
		return chatColor;
	}

	@Override
	public T setColor(Color rgb) {
		color = rgb;
		return getThis();
	}

	@Override
	public T setColor(ChatColor color) {
		this.chatColor = color;
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
		return color != null;
	}
	
	@Override
	public boolean hasColor() {
		return color != null;
	}
	
	@Override
	public String toJsonText() {
		StringWriter w = new StringWriter();
		SNBTWriter writer = new SNBTWriter(w);
		try {
			writer.writeCompoundTag();
			@SuppressWarnings("unchecked")
			NBTSerializationHandler<ChatComponent> handler = (NBTSerializationHandler<ChatComponent>) getNBTHandler();
			handler.serialize(this, writer);
			writer.writeEndTag();
		} catch (IOException e) {
			throw new NBTException("Error while writing component!", e);
		}
		return w.toString();
	}
	
	@Override
	public String toLegacyText() {
		return ChatUtil.componentToLegacy(this);
	}
	
	@Override
	public String toString() {
		return toJsonText();
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
