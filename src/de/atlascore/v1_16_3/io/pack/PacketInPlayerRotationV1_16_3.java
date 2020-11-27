package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInPlayerRotation;

public class PacketInPlayerRotationV1_16_3 extends AbstractPacket implements PacketInPlayerRotation {

	public PacketInPlayerRotationV1_16_3() {
		super(0x14, V1_16_3.version);
	}

	private float yaw,pitch;
	private boolean onGround;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		yaw = input.readFloat();
		pitch = input.readFloat();
		onGround = input.readBoolean();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

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
