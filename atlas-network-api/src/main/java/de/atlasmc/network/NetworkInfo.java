package de.atlasmc.network;

import java.util.List;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.configuration.file.JsonConfiguration;

public interface NetworkInfo {
	
	int getSlots();
	
	int getSlotDistance();
	
	@NotNull
	SlotMode getSlotMode();
	
	@Nullable
	String getMotd();
	
	@Nullable
	String getProtocolText();
	
	/**
	 * Returns the Base64 encoded Server Icon prefixed with "data:image/png;base64,"
	 * @return icon
	 */
	@Nullable
	String getServerIcon();
	
	@NotNull
	List<String> getPlayerInfo();
	
	@NotNull
	JsonConfiguration getStatusInfo(int protocol);
	
	public static enum SlotMode {
		/**
		 * Displays the slots dynamic as current user (player/players + slotDistance)
		 */
		DYNAMIC,
		/**
		 * Displays the slots as current user (players)/(slots)
		 */
		NORMAL,
		/**
		 * Displays the slots as (slots)/(slots + slot distance)
		 */
		FIXED
	}

}
