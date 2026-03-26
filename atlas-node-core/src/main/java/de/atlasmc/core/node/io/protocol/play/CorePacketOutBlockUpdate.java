package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutBlockUpdate;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockUpdate implements PacketCodec<PacketOutBlockUpdate> {
	
	@Override
	public void deserialize(PacketOutBlockUpdate packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.blockStateID = readVarInt(in);
	}

	@Override
	public void serialize(PacketOutBlockUpdate packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		writeVarInt(packet.blockStateID, out);
	}

	@Override
	public PacketOutBlockUpdate createPacketData() {
		return new PacketOutBlockUpdate();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutBlockUpdate.class);
	}

}
