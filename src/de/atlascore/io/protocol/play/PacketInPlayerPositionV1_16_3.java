package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerPosition;

public class PacketInPlayerPositionV1_16_3 extends AbstractPacket implements PacketInPlayerPosition {

	public PacketInPlayerPositionV1_16_3() {
		super(0x12, V1_16_3.version);
	}
	
	private double x,feedy,z;
	private boolean onGround;

	@Override
	public void read(int length, DataInput input) throws IOException {
		x = input.readDouble();
		feedy = input.readDouble();
		z = input.readDouble();
		onGround = input.readBoolean();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public double X() {
		return x;
	}

	@Override
	public double FeedY() {
		return feedy;
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
