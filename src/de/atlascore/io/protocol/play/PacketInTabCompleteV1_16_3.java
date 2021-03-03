package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInTabComplete;

public class PacketInTabCompleteV1_16_3 extends AbstractPacket implements PacketInTabComplete {

	public PacketInTabCompleteV1_16_3() {
		super(0x06, V1_16_3.version);
	}
	
	private int transactionID;
	private String text;

	@Override
	public void read(int length, DataInput input) throws IOException {
		transactionID = readVarInt(input);
		text = readString(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public String getText() {
		return text;
	}

}
