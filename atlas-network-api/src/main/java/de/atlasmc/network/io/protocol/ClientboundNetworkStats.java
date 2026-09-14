package de.atlasmc.network.io.protocol;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = AtlasPacket.CLIENT_NETWORK_STATS, definition = "network_stats")
public class ClientboundNetworkStats extends AbstractPacket implements AtlasPacketClientbound {

	public int 
	onlinePlayers,
	servers, 
	maxPlayers, 
	nodes;
	public boolean maintenance;

	@Override
	public int getDefaultID() {
		return AtlasPacket.CLIENT_NETWORK_STATS;
	}

}
