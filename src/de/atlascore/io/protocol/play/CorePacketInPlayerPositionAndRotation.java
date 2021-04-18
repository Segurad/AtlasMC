package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerPositionAndRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerPositionAndRotation extends AbstractPacket implements PacketInPlayerPositionAndRotation {

	public CorePacketInPlayerPositionAndRotation() {
		super(0x13, CoreProtocolAdapter.VERSION);	
	}

	private double x,feety,z;
	private float yaw,pitch;
	private boolean onGround;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		feety = in.readDouble();
		z = in.readDouble();
		yaw = in.readFloat();
		pitch = in.readFloat();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(feety);
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		out.writeBoolean(onGround);
	}

	@Override
	public double X() {
		return x;
	}

	@Override
	public double FeetY() {
		return feety;
	}

	@Override
	public double Z() {
		return z;
	}

	@Override
	public float Yaw() {
		return yaw;
	}

	@Override
	public float Pitch() {
		return pitch;
	}

	@Override
	public boolean OnGround() {
		return onGround;
	}
	
	

}
