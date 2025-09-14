package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.event.player.PlayerAnimationEvent.PlayerAnimationType;

@DefaultPacketID(packetID = PacketPlay.IN_SWING_ARM, definition = "swing")
public class PacketInSwingArm extends AbstractPacket implements PacketPlayIn {
	
	public PlayerAnimationType hand;
	
	@Override
	public int getDefaultID() {
		return IN_SWING_ARM;
	}

}
