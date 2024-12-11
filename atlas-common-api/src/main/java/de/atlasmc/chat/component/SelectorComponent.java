package de.atlasmc.chat.component;

import java.io.IOException;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class SelectorComponent extends AbstractBaseComponent<SelectorComponent> {
	
	public static final CharKey
	JSON_SELECTOR = CharKey.literal("selector"),
	JSON_SEPARATOR = CharKey.literal("separator");
	
	private String selector;
	private ChatComponent separator;
	
	public SelectorComponent() {}
	
	public SelectorComponent(String selector, ChatComponent separator) {
		this.selector = selector;
		this.separator = separator;
	}
	
	public String getSelector() {
		return selector;
	}
	
	public ChatComponent getSeparator() {
		return separator;
	}
	
	@Override
	public void addContents(NBTWriter writer) throws IOException {
		super.addContents(writer);
		writer.writeStringTag(JSON_SELECTOR, selector);
		if (separator != null) {
			writer.writeCompoundTag(JSON_SEPARATOR);
			separator.addContents(writer);
			writer.writeEndTag();
		}
	}
	
	@Override
	protected String getType() {
		return "selector";
	}
	
	@Override
	protected SelectorComponent getThis() {
		return this;
	}

}
