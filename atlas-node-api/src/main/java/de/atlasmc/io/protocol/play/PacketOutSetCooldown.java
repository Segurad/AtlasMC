package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_COOLDOWN, definition = "cooldown")
public class PacketOutSetCooldown extends AbstractPacket implements PacketPlayOut {
	
	public int itemID;
	public int cooldown;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_COOLDOWN;
	}

}
