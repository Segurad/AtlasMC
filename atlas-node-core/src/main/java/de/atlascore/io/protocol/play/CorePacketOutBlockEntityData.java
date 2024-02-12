package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData.TileUpdateAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockEntityData implements PacketIO<PacketOutBlockEntityData> {

	@Override
	public void read(PacketOutBlockEntityData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setAction(TileUpdateAction.getByID(readVarInt(in)));
		byte[] data = new byte[in.readableBytes()];
		in.readBytes(data);
		packet.setData(data);
	}

	@Override
	public void write(PacketOutBlockEntityData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getAction().getID(), out);
		out.writeBytes(packet.getData());
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
