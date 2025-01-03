package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutTagQueryResponse;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTagQueryResponse implements PacketIO<PacketOutTagQueryResponse> {

	@Override
	public void read(PacketOutTagQueryResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.transactionID = readVarInt(in);
		NBTNIOReader reader = new NBTNIOReader(in, true);
		packet.nbt = reader.readNBT();
		reader.close();
	}

	@Override
	public void write(PacketOutTagQueryResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.transactionID, out);
		if (packet.nbt == null) {
			out.writeByte(0);
			return;
		}
		NBTNIOWriter writer = new NBTNIOWriter(out, true);
		writer.writeNBT(packet.nbt);
		writer.close();
	}

	@Override
	public PacketOutTagQueryResponse createPacketData() {
		return new PacketOutTagQueryResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutTagQueryResponse.class);
	}

}
