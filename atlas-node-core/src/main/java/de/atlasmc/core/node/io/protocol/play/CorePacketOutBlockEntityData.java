package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutBlockEntityData;
import de.atlasmc.node.io.protocol.play.PacketOutBlockEntityData.TileUpdateAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockEntityData implements PacketIO<PacketOutBlockEntityData> {

	@Override
	public void read(PacketOutBlockEntityData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.action = TileUpdateAction.getByID(readVarInt(in));
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(PacketOutBlockEntityData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		writeVarInt(packet.action.getID(), out);
		out.writeBytes(packet.data);
	}

	@Override
	public PacketOutBlockEntityData createPacketData() {
		return new PacketOutBlockEntityData();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutBlockEntityData.class);
	}

}
