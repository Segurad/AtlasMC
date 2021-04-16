package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInVehicleMove;
import io.netty.buffer.ByteBuf;

public class PacketInVehicleMoveV1_16_3 extends AbstractPacket implements PacketInVehicleMove {

	public PacketInVehicleMoveV1_16_3() {
		super(0x16, V1_16_3.version);
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
	public double X() {	
		return x;
	}

	@Override
	public double Y() {
		return y;
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

}
