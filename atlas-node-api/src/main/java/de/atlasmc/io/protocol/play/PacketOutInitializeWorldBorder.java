package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_INITIALIZE_WORLD_BORDER)
public class PacketOutInitializeWorldBorder extends AbstractPacket implements PacketPlayOut {

	public double x;
	public double z;
	public double oldDiameter;
	public double newDiameter;
	public long speed;
	public int portalTeleportBoundary;
	public int warningBlocks;
	public int warningTime;
	
	@Override
	public int getDefaultID() {
		return OUT_INITIALIZE_WORLD_BORDER;
	}
	
}
