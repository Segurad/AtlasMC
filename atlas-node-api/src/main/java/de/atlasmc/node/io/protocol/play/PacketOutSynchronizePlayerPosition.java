package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.util.TeleportFlags;

@DefaultPacketID(packetID = PacketPlay.OUT_SYNCHRONIZE_PLAYER_POSITION, definition = "player_position")
public class PacketOutSynchronizePlayerPosition extends AbstractPacket implements PacketPlayOut {
	
	public double x;
	public double y;
	public double z;
	public double velocityX;
	public double velocityY;
	public double velocityZ;
	public float yaw;
	public float pitch;
	/**
	 * @see TeleportFlags
	 */
	public int flags;
	public int teleportID;

	@Override
	public int getDefaultID() {
		return OUT_SYNCHRONIZE_PLAYER_POSITION;
	}

}
