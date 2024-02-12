package de.atlasmc.entity;

import de.atlasmc.chat.Chat;
import de.atlasmc.command.CommandSender;

public interface MinecartCommandBlock extends CommandSender {
	
	public String getCommand();
	
	public Chat getLastMessage();

	public void setCommand(String command);
	
	/**
	 * While {@link #sendMessage(Chat)} does only set the last message if {@link #isTrackingOutput()} is true,
	 * this method will always set the last output
	 * @param message
	 */
	public void setLastMessage(Chat message);

	public void setRedstoneSignal(int signal);
	
	public int getRedstoneSignal();

	public void setTrackOutput(boolean track);
	
	public boolean isTrackingOutput();
	
}
