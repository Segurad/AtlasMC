package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInInteractEntity;
import io.netty.buffer.ByteBuf;

public class CorePacketInInteractEntity extends CoreAbstractHandler<PacketInInteractEntity> {

	@Override
	public void read(PacketInInteractEntity packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setType(readVarInt(in));
		packet.setHand(readVarInt(in));
		packet.setX(in.readFloat());
		packet.setY(in.readFloat());
		packet.setZ(in.readFloat());
		packet.setSneaking(in.readBoolean());
	}

	@Override
	public void write(PacketInInteractEntity packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		writeVarInt(packet.getType(), out);
		writeVarInt(packet.getHand(), out);
		out.writeFloat(packet.getX());
		out.writeFloat(packet.getY());
		out.writeFloat(packet.getZ());
		out.writeBoolean(packet.isSneaking());
	}

	@Override
	public PacketInInteractEntity createPacketData() {
		return new PacketInInteractEntity();
	}

}
