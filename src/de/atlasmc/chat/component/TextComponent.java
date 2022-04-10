package de.atlasmc.chat.component;

import de.atlasmc.util.JsonBuffer;

public class TextComponent extends BaseComponent {

	public static final String
	JSON_TEXT = "text";
	
	private String text;
	
	public TextComponent(String text) {
		if (text == null)
			throw new IllegalArgumentException("Text can not be null!");
		this.text = text;
	}

	public TextComponent() {}

	public void setText(String text) {
		if (text == null)
			throw new IllegalAccessError("Text can not be null!");
		this.text = text;
	}

	public String getValue() {
		return text;
	}
	
	public void setValue(String value) {
		this.text = value;
	}
	
	@Override
	public void addContents(JsonBuffer buff) {
		buff.appendText(JSON_TEXT, text);
		super.addContents(buff);
	}

}
