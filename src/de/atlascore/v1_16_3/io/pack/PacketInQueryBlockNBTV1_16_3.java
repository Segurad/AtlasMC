package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInQueryBlockNBT;

public class PacketInQueryBlockNBTV1_16_3 extends AbstractPacket implements PacketInQueryBlockNBT {

	public PacketInQueryBlockNBTV1_16_3() {
		super(0x01, V1_16_3.version);
	}

	private int transactionID;
	private SimpleLocation loc;
	
	@Override
	public void read(int length, DataInputStream input) throws IOException {
		transactionID = readVarInt(input);
		loc = readPosition(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public SimpleLocation getLocation() {
		return loc;
	}

}
