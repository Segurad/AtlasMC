package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData.TileUpdateAction;
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
