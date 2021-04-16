package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerPosition;
import io.netty.buffer.ByteBuf;

public class PacketInPlayerPositionV1_16_3 extends AbstractPacket implements PacketInPlayerPosition {

	public PacketInPlayerPositionV1_16_3() {
		super(0x12, V1_16_3.version);
	}
	
	private double x,feety,z;
	private boolean onGround;

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		feety = in.readDouble();
		z = in.readDouble();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(feety);
		out.writeDouble(z);
		out.writeBoolean(onGround);
	}

	@Override
	public double X() {
		return x;
	}

	@Override
	public double FeedY() {
		return feety;
	}

	@Override
	public double Z() {
		return z;
	}

	@Override
	public boolean OnGround() {
		return onGround;
	}
	
	

}
