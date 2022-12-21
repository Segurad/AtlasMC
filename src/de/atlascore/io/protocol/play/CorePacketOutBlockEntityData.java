package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData;
import de.atlasmc.io.protocol.play.PacketOutBlockEntityData.TileUpdateAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockEntityData extends CoreAbstractHandler<PacketOutBlockEntityData> {

	@Override
	public void read(PacketOutBlockEntityData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setAction(TileUpdateAction.getByID(in.readUnsignedByte()));
		byte[] data = new byte[in.readableBytes()];
		in.readBytes(data);
		packet.setData(data);
	}

	@Override
	public void write(PacketOutBlockEntityData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		out.writeByte(packet.getAction().getID());
		out.writeBytes(packet.getData());
	}

	@Override
	public PacketOutBlockEntityData createPacketData() {
		return new PacketOutBlockEntityData();
	}

}
