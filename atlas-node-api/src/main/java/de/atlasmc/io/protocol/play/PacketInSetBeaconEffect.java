package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_BEACON_EFFECT, definition = "beacon_effect")
public class PacketInSetBeaconEffect extends AbstractPacket implements PacketPlayIn {
	
	public boolean hasPrimaryEffect;
	public boolean hasSecondaryEffect;
	public int primaryEffect;
	public int secondaryEffect;
	
	@Override
	public int getDefaultID() {
		return IN_SET_BEACON_EFFECT;
	}

}
