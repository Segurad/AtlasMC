package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_ROTATION, definition = "move_player_rot")
public class PacketInSetPlayerRotation extends AbstractPacket implements PacketPlayIn {
	
	public float yaw;
	public float pitch;
	/**
	 * <ul>
	 * <li>0x01 = on ground</li>
	 * <li>0x02 = push against wall</li>
	 * </ul>
	 */
	public int flags;
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_ROTATION;
	}

}
