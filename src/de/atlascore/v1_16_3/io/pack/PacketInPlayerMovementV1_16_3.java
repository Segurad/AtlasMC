package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInPlayerMovement;

public class PacketInPlayerMovementV1_16_3 extends AbstractPacket implements PacketInPlayerMovement {

	public PacketInPlayerMovementV1_16_3() {
		super(0x15, V1_16_3.version);
	}
	
	private boolean onGround;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public boolean OnGround() {
		return onGround;
	}
	
	

}
