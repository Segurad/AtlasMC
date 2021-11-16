package de.atlasmc.chat;

import de.atlasmc.chat.component.ChatComponent;

public interface ChatAdapter {

	public void sendMessage(ChatChannel channel, ChatComponent chat);
	
	public ChatChannel getActiveChannel();
	
	public void setActiveChannel(ChatChannel channel);
	
	public boolean isChannelMuted(ChatChannel channel);
	
	public void setChannelMuted(ChatChannel channel, boolean muted);
	
}
