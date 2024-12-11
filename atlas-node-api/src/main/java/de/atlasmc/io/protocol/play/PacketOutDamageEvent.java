package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_DAMAGE_EVENT, definition = "damage_event")
public class PacketOutDamageEvent extends AbstractPacket implements PacketPlayOut {

	public int entityID;
	public int damageType;
	public int sourceCauseID;
	public int sourceDirectID;
	public boolean hasPosition;
	public double x;
	public double y;
	public double z;
	
	@Override
	public int getDefaultID() {
		return OUT_DAMAGE_EVENT;
	}

}
