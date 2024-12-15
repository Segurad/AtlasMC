package de.atlasmc.io.protocol.play;

import de.atlasmc.event.player.PlayerAnimationEvent.PlayerAnimationType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SWING_ARM, definition = "swing_arm")
public class PacketInSwingArm extends AbstractPacket implements PacketPlayIn {
	
	public PlayerAnimationType hand;
	
	@Override
	public int getDefaultID() {
		return IN_SWING_ARM;
	}

}
