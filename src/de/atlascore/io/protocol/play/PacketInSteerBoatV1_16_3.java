package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSteerBoat;
import io.netty.buffer.ByteBuf;

public class PacketInSteerBoatV1_16_3 extends AbstractPacket implements PacketInSteerBoat {

	public PacketInSteerBoatV1_16_3() {
		super(0x17, V1_16_3.version);
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
	public boolean LeftPaddleTurning() {
		return leftPaddleturning;
	}

	@Override
	public boolean RightPaddleTurning() {
		return rightPaddleturning;
	}

}
