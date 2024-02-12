package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_SET_PLAYER_ON_GROUND)
public class PacketInSetPlayerOnGround extends AbstractPacket implements PacketPlayIn {
	
	private boolean onGround;
	
	public boolean isOnGround() {
		return onGround;
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_ON_GROUND;
	}

}
