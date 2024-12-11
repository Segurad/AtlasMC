package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_ABILITIES)
public class PacketInPlayerAbilities extends AbstractPacket implements PacketPlayIn {
	
	private int flags;
	
	public int getFlags() {
		return flags;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	public boolean isFlying() {
		return (flags & 0x02) == 0x02;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_ABILITIES;
	}

}
