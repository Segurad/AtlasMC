package de.atlasmc.chat;

public interface ChatAdapter {

	public void sendMessage(ChatChannel channel, Chat chat);
	
	public ChatChannel getActiveChannel();
	
	public void setActiveChannel(ChatChannel channel);
	
	public boolean isChannelMuted(ChatChannel channel);
	
	public void setChannelMuted(ChatChannel channel, boolean muted);
	
}
