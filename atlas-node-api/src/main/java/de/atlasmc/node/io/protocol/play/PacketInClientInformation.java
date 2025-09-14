package de.atlasmc.node.io.protocol.play;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CLIENT_INFORMATION, definition = "client_information")
public class PacketInClientInformation extends AbstractPacket implements PacketPlayIn {
	
	public String local;
	public int viewDistance;
	public ChatMode chatMode;
	public boolean chatColors;
	public int skinParts;
	public int mainHand;
	public boolean enableTextFiltering;
	public boolean allowServerListing;
	
	public int getDefaultID() {
		return IN_CLIENT_INFORMATION;
	}

}
