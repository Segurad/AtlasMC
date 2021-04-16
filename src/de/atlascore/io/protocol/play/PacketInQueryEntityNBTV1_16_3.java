package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInQueryEntityNBT;
import io.netty.buffer.ByteBuf;

public class PacketInQueryEntityNBTV1_16_3 extends AbstractPacket implements PacketInQueryEntityNBT {

	public PacketInQueryEntityNBTV1_16_3() {
		super(0x0D, V1_16_3.version);
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
