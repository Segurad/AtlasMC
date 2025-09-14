package de.atlasmc.node.io.protocol;

import de.atlasmc.chat.ChatMode;

/**
 * Stores some client side settings of the player
 */
public interface PlayerSettings {
	
	/**
	 * Returns the language selected by the client
	 * @return
	 */
	String getLocal();
	
	/**
	 * Sets the language selected by the client.
	 * Should only be used to change the received local.
	 * @param local
	 */
	void setLocal(String local);

	int getViewDistance();

	void setViewDistance(int distance);

	int getMainHand();

	void setMainHand(int mainHand);

	void setSkinParts(int skinparts);

	int getSkinParts();

	void setChatMode(ChatMode mode);

	void setChatColor(boolean color);

	boolean getChatColor();

	ChatMode getChatMode();

	boolean getAllowServerListing();
	
	void setAllowServerListing(boolean allowServerListing);
	
	boolean hasTextFiltering();
	
	void setTextFiltering(boolean textFiltering);
	
}
