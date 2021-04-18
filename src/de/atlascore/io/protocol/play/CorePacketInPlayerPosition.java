package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerPosition extends AbstractPacket implements PacketInPlayerPosition {

	public CorePacketInPlayerPosition() {
		super(0x12, CoreProtocolAdapter.VERSION);
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
