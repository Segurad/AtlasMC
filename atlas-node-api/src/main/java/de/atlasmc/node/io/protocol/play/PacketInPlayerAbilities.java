package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_ABILITIES, definition = "player_abilities")
public class PacketInPlayerAbilities extends AbstractPacket implements PacketPlayIn {
	
	/**
	 * <ul>
	 * <li>0x02 = is flying</li>
	 * </ul>
	 */
	public int flags;
	
	public boolean isFlying() {
		return (flags & 0x02) == 0x02;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_ABILITIES;
	}

}
