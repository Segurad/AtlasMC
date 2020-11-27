package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInPlayerPositionAndRotation;

public class PacketInPlayerPositionAndRotationV1_16_3 extends AbstractPacket implements PacketInPlayerPositionAndRotation {

	public PacketInPlayerPositionAndRotationV1_16_3() {
		super(0x13, V1_16_3.version);	
	}

	private double x,feety,z;
	private float yaw,pitch;
	private boolean onGround;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		x = input.readDouble();
		feety = input.readDouble();
		z = input.readDouble();
		yaw = input.readFloat();
		pitch = input.readFloat();
		onGround = input.readBoolean();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

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
