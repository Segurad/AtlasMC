package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_POSITION_AND_ROTATION, definition = "move_player_pos_rot")
public class PacketInSetPlayerPositionAndRotation extends AbstractPacket implements PacketPlayIn {
	
	public double x; 
	public double feetY; 
	public double z;
	/**
	 * <ul>
	 * <li>0x01 = on ground</li>
	 * <li>0x02 = push against wall</li>
	 * </ul>
	 */
	public int flags;
	public float yaw;
	public float pitch;
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_POSITION_AND_ROTATION;
	}

}
