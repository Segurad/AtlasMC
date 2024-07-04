package de.atlasmc.chat.component;

import de.atlasmc.util.JsonBuffer;

public class TextComponent extends AbstractBaseComponent<TextComponent> {

	public static final String
	JSON_TEXT = "text";
	
	private String text;
	
	public TextComponent(String text) {
		this.text = text;
	}

	public TextComponent() {}

	public TextComponent setValue(String text) {
		this.text = text;
		return this;
	}
	
	public String getValue() {
		return text;
	}
	
	@Override
	public void addContents(JsonBuffer buff) {
		buff.appendText(JSON_TEXT, text);
		super.addContents(buff);
	}

	@Override
	protected TextComponent getThis() {
		return this;
	}

}
