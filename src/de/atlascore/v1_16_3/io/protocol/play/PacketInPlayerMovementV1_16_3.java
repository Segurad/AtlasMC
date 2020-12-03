package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerMovement;

public class PacketInPlayerMovementV1_16_3 extends AbstractPacket implements PacketInPlayerMovement {

	public PacketInPlayerMovementV1_16_3() {
		super(0x15, V1_16_3.version);
	}
	
	private boolean onGround;

	@Override
	public void read(int length, DataInput input) throws IOException {
		
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public boolean OnGround() {
		return onGround;
	}
	
	

}
