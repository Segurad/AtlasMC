package de.atlasmc.io.protocol;

import de.atlasmc.chat.ChatType;

/**
 * Stores some client side settings of the player
 */
public interface PlayerSettings {
	
	/**
	 * Returns the language selected by the client
	 * @return
	 */
	public String getLocal();
	
	/**
	 * Sets the language selected by the client.
	 * Should only be used to change the received local.
	 * @param local
	 */
	public void setLocal(String local);

	public int getViewDistance();

	public void setViewDistance(int distance);

	public int getMainHand();

	public void setMainHand(int mainHand);

	public void setSkinParts(int skinparts);

	public int getSkinParts();

	public void setChatMode(ChatType mode);

	public void setChatColor(boolean color);

	public boolean getChatColor();

	public ChatType getChatMode();

}
