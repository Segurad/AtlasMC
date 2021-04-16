package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInQueryBlockNBT;
import io.netty.buffer.ByteBuf;

public class PacketInQueryBlockNBTV1_16_3 extends AbstractPacket implements PacketInQueryBlockNBT {

	public PacketInQueryBlockNBTV1_16_3() {
		super(0x01, V1_16_3.version);
	}

	private int transactionID;
	private SimpleLocation loc;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		transactionID = readVarInt(in);
		loc = readPosition(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(transactionID, out);
		writePosition(loc, out);
	}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public SimpleLocation getLocation() {
		return loc;
	}

}
