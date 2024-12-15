package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_INPUT, definition = "player_input")
public class PacketInPlayerInput extends AbstractPacket implements PacketPlayIn {
	
	public float sideways;
	public float forward;
	/**
	 * <ul>
	 * <li>0x01 = jump</li>
	 * <li>0x02 = unmount</li>
	 * </ul>
	 */
	public int flags;
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_INPUT;
	}

}
