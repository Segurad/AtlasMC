package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInQueryEntityNBT;
import io.netty.buffer.ByteBuf;

public class CorePacketInQueryEntityNBT extends AbstractPacket implements PacketInQueryEntityNBT {

	public CorePacketInQueryEntityNBT() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	private int transactionID, entityID;

	@Override
	public void read(ByteBuf in) throws IOException {
		transactionID = readVarInt(in);
		entityID = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(transactionID, out);
		writeVarInt(entityID, out);
	}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

}
