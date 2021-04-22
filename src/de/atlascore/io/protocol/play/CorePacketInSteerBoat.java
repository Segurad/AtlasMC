package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSteerBoat;
import io.netty.buffer.ByteBuf;

public class CorePacketInSteerBoat extends AbstractPacket implements PacketInSteerBoat {

	public CorePacketInSteerBoat() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	private boolean rightPaddleturning,leftPaddleturning;

	@Override
	public void read(ByteBuf in) throws IOException {
		rightPaddleturning = in.readBoolean();
		leftPaddleturning = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeBoolean(rightPaddleturning);
		out.writeBoolean(leftPaddleturning);
	}

	@Override
	public boolean getLeftPaddleTurning() {
		return leftPaddleturning;
	}

	@Override
	public boolean getRightPaddleTurning() {
		return rightPaddleturning;
	}

}
