package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInQueryEntityNBT;

public class PacketInQueryEntityNBTV1_16_3 extends AbstractPacket implements PacketInQueryEntityNBT {

	public PacketInQueryEntityNBTV1_16_3() {
		super(0x0D, V1_16_3.version);
	}
	
	private int transactionID, entityID;

	@Override
	public void read(int length, DataInput input) throws IOException {
		transactionID = readVarInt(input);
		entityID = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

}
