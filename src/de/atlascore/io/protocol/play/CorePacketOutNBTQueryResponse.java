package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutNBTQueryResponse;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CorePacketOutNBTQueryResponse extends AbstractPacket implements PacketOutNBTQueryResponse {

	private int transactionID;
	private ByteBuf buf;
	
	public CorePacketOutNBTQueryResponse() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutNBTQueryResponse(int transactionID, NBT nbt) {
		this();
		this.transactionID = transactionID;
		if (nbt == null) {
			buf = Unpooled.buffer(1);
			buf.writeByte(0);
		} else {
			buf = Unpooled.buffer();
			try {
				new NBTNIOWriter(buf).writeNBT(nbt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		transactionID = readVarInt(in);
		buf = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(transactionID, out);
		out.writeBytes(buf);
	}

	@Override
	public int getTransactionID() {
		return transactionID;
	}

	@Override
	public NBT getNBT() {
		try {
			return new NBTNIOReader(buf).readNBT();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NBTReader getNBTReader() {
		return new NBTNIOReader(buf);
	}

}
