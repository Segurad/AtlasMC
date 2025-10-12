package de.atlasmc.node.io.protocol.common;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketClientInformation extends AbstractPacket {

	public String local;
	public int viewDistance;
	public ChatMode chatMode;
	public boolean chatColors;
	public int skinParts;
	public int mainHand;
	public boolean enableTextFiltering;
	public boolean allowServerListing;
	public int particleStatus;
	
}
