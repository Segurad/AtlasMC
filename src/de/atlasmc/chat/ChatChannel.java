package de.atlasmc.chat;

import de.atlasmc.chat.component.ChatComponent;

public interface ChatChannel {
	
	public ChannelType getType();
	
	public boolean isMuteable();
	
	public String getName();
	
	/**
	 * 
	 * @return the prefix or null
	 */
	public ChatComponent getPrefix();
	
	/**
	 * Client chat settings
	 */
	public static enum ChannelType {
		CHAT,
		SYSTEM,
		GAMEINFO
	}

}
