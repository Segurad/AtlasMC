package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SYNCHRONIZE_PLAYER_POSITION, definition = "player_position")
public class PacketOutSynchronizePlayerPosition extends AbstractPacket implements PacketPlayOut {
	
	public double x;
	public double y;
	public double z;
	public float yaw;
	public float pitch;
	/**
	 * <ul>
	 * <li>0x01 = X</li>
	 * <li>0x02 = Y</li>
	 * <li>0x04 = Z</li>
	 * <li>0x08 = pitch</li>
	 * <li>0x10 = yaw</li>
	 * </ul>
	 * If a field is has 0 bit value it is absolute otherwise relative
	 */
	public int flags;
	public int teleportID;

	@Override
	public int getDefaultID() {
		return OUT_SYNCHRONIZE_PLAYER_POSITION;
	}

}
