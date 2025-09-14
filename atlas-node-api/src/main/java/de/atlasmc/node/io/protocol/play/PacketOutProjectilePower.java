package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_PROJECTILE_POWER, definition = "projectile_power")
public class PacketOutProjectilePower extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public double power;
	
	@Override
	public int getDefaultID() {
		return OUT_PROJECTILE_POWER;
	}

}
