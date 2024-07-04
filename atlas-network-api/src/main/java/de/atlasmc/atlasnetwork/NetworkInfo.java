package de.atlasmc.atlasnetwork;

import java.util.List;

public interface NetworkInfo {
	
	int getSlots();
	
	int getSlotDistance();
	
	SlotMode getSlotMode();
	
	String getMotd();
	
	String getProtocolText();
	
	String getServerIcon();
	
	List<String> getPlayerInfo();
	
	String getStatusInfo(int protocol);
	
	public static enum SlotMode {
		/**
		 * Displays the slots dynamic as current user count/current user count + {@link NetworkInfo#getSlotDistance()}
		 */
		DYNAMIC,
		/**
		 * Displays the slots as current user count/{@link NetworkInfo#getSlots()}
		 */
		NORMAL,
		/**
		 * Displays the slots as current user count/value with base {@link NetworkInfo#getSlotDistance()} that fits the current user count
		 */
		BASE,
		/**
		 * Displays the slots as {@link NetworkInfo#getSlots()}/{@link NetworkInfo#getSlots()}+{@link NetworkInfo#getSlotDistance()}
		 */
		FIXED
	}

}
