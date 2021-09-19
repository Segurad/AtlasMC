package de.atlasmc.chat.component;

public interface ChatComponent {
	
	public String getLegacyText();
	public String getJsonText();
	public boolean contains(ChatComponent displayName);
	
	

}
