package de.atlasmc.chat.component;

import java.io.IOException;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class TextComponent extends AbstractBaseComponent<TextComponent> {

	public static final CharKey
	JSON_TEXT = CharKey.literal("text");
	
	private String text;
	
	public TextComponent(String text) {
		this.text = text;
	}

	public TextComponent() {}
	
	@Override
	protected String getType() {
		return "text";
	}

	public TextComponent setValue(String text) {
		this.text = text;
		return this;
	}
	
	public String getValue() {
		return text;
	}
	
	@Override
	public void addContents(NBTWriter writer) throws IOException {
		super.addContents(writer);
		writer.writeStringTag(JSON_TEXT, text);
	}

	@Override
	protected TextComponent getThis() {
		return this;
	}

}
