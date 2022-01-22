package de.atlasmc.entity;

import de.atlasmc.chat.component.ChatComponent;

public interface MinecartCommandBlock {
	
	public String getCommand();
	
	public ChatComponent getLastOutput();

	public void setCommand(String command);
	
	public void setLastOutput(ChatComponent out);
	
}
