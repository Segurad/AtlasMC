package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInVehicleMove;
import io.netty.buffer.ByteBuf;

public class CorePacketInVehicleMove extends AbstractPacket implements PacketInVehicleMove {

	public CorePacketInVehicleMove() {
		super(0x16, CoreProtocolAdapter.VERSION);
	}
	
	private double x,y,z;
	private float yaw,pitch;

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		yaw = in.readFloat();
		pitch = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
	}

	@Override
	public double getX() {	
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public float getYaw() {
		return yaw;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

}
