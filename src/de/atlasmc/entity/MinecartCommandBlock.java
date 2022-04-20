package de.atlasmc.entity;

import de.atlasmc.chat.Chat;

public interface MinecartCommandBlock {
	
	public String getCommand();
	
	public Chat getLastOutput();

	public void setCommand(String command);
	
	public void setLastOutput(Chat out);

	public void setRedstoneSignal(int signal);
	
	public int getRedstoneSignal();

	public void setTrackOutput(boolean track);
	
	public boolean isTrackingOutput();
	
}
