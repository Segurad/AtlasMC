package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInTabComplete;
import io.netty.buffer.ByteBuf;

public class CorePacketInTabComplete extends AbstractPacket implements PacketInTabComplete {

	public CorePacketInTabComplete() {
		super(CoreProtocolAdapter.VERSION);
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
