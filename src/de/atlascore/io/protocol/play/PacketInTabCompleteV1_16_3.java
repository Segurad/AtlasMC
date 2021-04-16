package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInTabComplete;
import io.netty.buffer.ByteBuf;

public class PacketInTabCompleteV1_16_3 extends AbstractPacket implements PacketInTabComplete {

	public PacketInTabCompleteV1_16_3() {
		super(0x06, V1_16_3.version);
	}
	
	private int transactionID;
	private String text;

	@Override
	public void read(ByteBuf in) throws IOException {
		transactionID = readVarInt(in);
		text = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(transactionID, out);
		writeString(text, out);
	}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public String getText() {
		return text;
	}

}
