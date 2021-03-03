package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInVehicleMove;

public class PacketInVehicleMoveV1_16_3 extends AbstractPacket implements PacketInVehicleMove {

	public PacketInVehicleMoveV1_16_3() {
		super(0x16, V1_16_3.version);
		// TODO Auto-generated constructor stub
	}
	
	private double x,y,z;
	private float yaw,pitch;

	@Override
	public void read(int length, DataInput input) throws IOException {
		x = input.readDouble();
		y = input.readDouble();
		z = input.readDouble();
		yaw = input.readFloat();
		pitch = input.readFloat();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

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
