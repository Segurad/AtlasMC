package de.atlasmc.atlasnetwork;

import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.JsonElement;

public interface ProxyConfig {

	AtomicReference<ProxyConfig> DEFAULT_CONFIG = new AtomicReference<ProxyConfig>();
	
	public static ProxyConfig getDefault() {
		return DEFAULT_CONFIG.get();
	}
	
	public static void setDefault(ProxyConfig newDefault) {
		DEFAULT_CONFIG.set(newDefault);
	}

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

	public void setServerIconBase64(String serverIcon);

	public void setMaintenance(boolean value);
}
