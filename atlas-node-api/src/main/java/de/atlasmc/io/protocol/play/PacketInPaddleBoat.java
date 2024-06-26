package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_PADDLE_BOAT)
public class PacketInPaddleBoat extends AbstractPacket implements PacketPlayIn {
	
	private boolean leftPaddle;
	private boolean rightPaddle;
	
	public boolean getLeftPaddleTurning() {
		return leftPaddle;
	}
	
	public boolean getRightPaddleTurning() {
		return rightPaddle;
	}
	
	public void setLeftPaddle(boolean leftPaddle) {
		this.leftPaddle = leftPaddle;
	}
	
	public void setRightPaddle(boolean rightPaddle) {
		this.rightPaddle = rightPaddle;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PADDLE_BOAT;
	}

}
