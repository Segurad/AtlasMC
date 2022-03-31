package de.atlasmc.chat.component;

import de.atlasmc.chat.Chat;

public class TextComponent extends AbstractComponent {

	private String text;
	
	public TextComponent() {}
	
	public TextComponent(String text) {
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public Chat toChat() {
		// TODO Auto-generated method stub
		return null;
	}

}
