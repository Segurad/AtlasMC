package de.atlasmc.entity;

import de.atlasmc.chat.ChatComponent;

public interface MinecartCommandBlock {
	
	public String getCommand();
	public ChatComponent getLastOutput();

}
