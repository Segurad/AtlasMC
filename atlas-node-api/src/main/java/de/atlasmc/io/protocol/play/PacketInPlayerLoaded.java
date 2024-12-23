package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_LOADED, definition = "player_loaded")
public class PacketInPlayerLoaded extends AbstractPacket implements PacketPlayIn {

	@Override
	public int getDefaultID() {
		return IN_PLAYER_LOADED;
	}

}
