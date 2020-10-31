package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInQueryEntityNBT;

public class PacketInQueryEntityNBTV1_16_3 extends AbstractPacket implements PacketInQueryEntityNBT {

	public PacketInQueryEntityNBTV1_16_3() {
		super(0x0D, V1_16_3.version);
	}
	
	private int transactionID, entityID;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		transactionID = readVarInt(input);
		entityID = readVarInt(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

}
