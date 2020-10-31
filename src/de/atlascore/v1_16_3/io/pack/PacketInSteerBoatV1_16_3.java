package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInSteerBoat;

public class PacketInSteerBoatV1_16_3 extends AbstractPacket implements PacketInSteerBoat {

	public PacketInSteerBoatV1_16_3() {
		super(0x17, V1_16_3.version);
	}
	
	private boolean rightPaddleturning,leftPaddleturning;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		rightPaddleturning = input.readBoolean();
		leftPaddleturning = input.readBoolean();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public boolean LeftPaddleTurning() {
		return leftPaddleturning;
	}

	@Override
	public boolean RightPaddleTurning() {
		return rightPaddleturning;
	}

}
