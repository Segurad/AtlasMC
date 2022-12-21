package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutNBTQueryResponse;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutNBTQueryResponse extends CoreAbstractHandler<PacketOutNBTQueryResponse> {

	@Override
	public void read(PacketOutNBTQueryResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTransactionID(readVarInt(in));
		NBTNIOReader reader = new NBTNIOReader(in);
		packet.setNBT(reader.readNBT());
		reader.close();
	}

	@Override
	public void write(PacketOutNBTQueryResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getTransactionID(), out);
		if (packet.getNBT() == null) {
			out.writeByte(0);
			return;
		}
		NBTNIOWriter writer = new NBTNIOWriter(out);
		writer.writeNBT(packet.getNBT());
		writer.close();
	}

	@Override
	public PacketOutNBTQueryResponse createPacketData() {
		return new PacketOutNBTQueryResponse();
	}

}
