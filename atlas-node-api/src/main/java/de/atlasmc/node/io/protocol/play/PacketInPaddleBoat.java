package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PADDLE_BOAT, definition = "paddle_boat")
public class PacketInPaddleBoat extends AbstractPacket implements PacketPlayIn {
	
	public boolean leftPaddle;
	public boolean rightPaddle;
	
	@Override
	public int getDefaultID() {
		return IN_PADDLE_BOAT;
	}

}
