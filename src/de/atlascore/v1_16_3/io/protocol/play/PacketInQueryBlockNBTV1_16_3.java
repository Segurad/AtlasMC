package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInQueryBlockNBT;

public class PacketInQueryBlockNBTV1_16_3 extends AbstractPacket implements PacketInQueryBlockNBT {

	public PacketInQueryBlockNBTV1_16_3() {
		super(0x01, V1_16_3.version);
	}

	private int transactionID;
	private SimpleLocation loc;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		transactionID = readVarInt(input);
		loc = readPosition(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public SimpleLocation getLocation() {
		return loc;
	}

}
