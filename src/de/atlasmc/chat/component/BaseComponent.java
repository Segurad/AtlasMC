package de.atlasmc.chat.component;

public class BaseComponent implements ChatComponent {

	@Override
	public String getLegacyText() {
		return null;
	}

	@Override
	public String getJsonText() {
		return null;
	}

	@Override
	public boolean contains(ChatComponent displayName) {
		// TODO Auto-generated method stub
		return false;
	}

}
