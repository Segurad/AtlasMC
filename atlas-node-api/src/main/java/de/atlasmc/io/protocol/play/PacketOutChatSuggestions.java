package de.atlasmc.io.protocol.play;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_CHAT_SUGGESTIONS, definition = "custom_chat_completion")
public class PacketOutChatSuggestions extends AbstractPacket implements PacketPlayOut {

	/**
	 * 0 = add
	 * 1 = remove
	 * 2 = set
	 */
	public int action;
	public List<String> entries;
	
	public void addEntry(String entry) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (entries == null)
			entries = new ArrayList<>();
		entries.add(entry);
	}
	
	@Override
	public int getDefaultID() {
		return OUT_CHAT_SUGGESTIONS;
	}
	
}
