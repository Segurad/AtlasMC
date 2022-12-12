package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_PLAYER_MOVEMENT)
public class PacketInPlayerMovement extends AbstractPacket implements PacketPlayIn {
	
	private boolean onGround;
	
	public boolean isOnGround() {
		return onGround;
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_MOVEMENT;
	}

}
