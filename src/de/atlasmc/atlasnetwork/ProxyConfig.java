package de.atlasmc.atlasnetwork;

import com.google.gson.JsonElement;

public interface ProxyConfig {

	public static ProxyConfig DEFAULT_CONFIG = null;

	public boolean isMaintenance();

	public int getMaxPlayers();
	
	public SlotDisplayMode getSlotDisplayMode();

	
	public static enum SlotDisplayMode {
		/**
		 * Displays MaxPlayerCount and OnlinePlayerCount
		 */
		NORMAL,
		/**
		 * Displays OnlinePlayerCount+1 andOnlinePlayerCount
		 */
		DYNAMIC,
		/**
		 * Displays 0 and 0
		 */
		NONE
	}


	public String getProtocolText();

	public String[] getPlayerInfo();

	public JsonElement getJsonMOTD();

	public String getServerIconBase64();
}
