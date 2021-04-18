package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInQueryBlockNBT;
import io.netty.buffer.ByteBuf;

public class CorePacketInQueryBlockNBT extends AbstractPacket implements PacketInQueryBlockNBT {

	public CorePacketInQueryBlockNBT() {
		super(0x01, CoreProtocolAdapter.VERSION);
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
