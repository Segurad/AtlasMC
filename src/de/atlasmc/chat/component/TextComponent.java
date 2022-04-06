package de.atlasmc.chat.component;

import de.atlasmc.util.JsonBuffer;

public class TextComponent extends BaseComponent {

	protected static final String
	JSON_TEXT = "text";
	
	private String text;
	
	public TextComponent(String text) {
		if (text == null)
			throw new IllegalArgumentException("Text can not be null!");
		this.text = text;
	}

	public void setText(String text) {
		if (text == null)
			throw new IllegalAccessError("Text can not be null!");
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	@Override
	public void addContents(JsonBuffer buff) {
		buff.appendText(JSON_TEXT, text);
		super.addContents(buff);
	}

}
