package de.atlasmc.io.protocol.configuration;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.IN_CLIENT_INFORMATION, definition = "client_information")
public class PacketInClientInformation extends AbstractPacket implements PacketConfigurationIn {

	public String local;
	public int viewDistance;
	public ChatMode chatMode;
	public boolean chatColors;
	public int skinParts;
	public int mainHand;
	public boolean enableTextFiltering;
	public boolean allowServerListing;
	
	@Override
	public int getDefaultID() {
		return IN_CLIENT_INFORMATION;
	}
	
}
