package de.atlasmc.io.protocol.play;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_PLAYER_INFO_REMOVE, definition = "player_info_remove")
public class PacketOutPlayerInfoRemove extends AbstractPacket implements PacketPlayOut {

	public List<UUID> players;
	
	public void addPlayer(UUID uuid) {
		if (players == null)
			players = new ArrayList<>();
		players.add(uuid);
	}
	
	@Override
	public int getDefaultID() {
		return OUT_PLAYER_INFO_REMOVE;
	}
	
}
