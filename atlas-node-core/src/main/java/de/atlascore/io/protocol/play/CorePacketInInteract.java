package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInInteract;
import io.netty.buffer.ByteBuf;

public class CorePacketInInteract implements PacketIO<PacketInInteract> {

	@Override
	public void read(PacketInInteract packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setType(readVarInt(in));
		packet.setX(in.readFloat());
		packet.setY(in.readFloat());
		packet.setZ(in.readFloat());
		packet.setHand(readVarInt(in));
		packet.setSneaking(in.readBoolean());
	}

	@Override
	public void write(PacketInInteract packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		writeVarInt(packet.getType(), out);
		out.writeFloat(packet.getX());
		out.writeFloat(packet.getY());
		out.writeFloat(packet.getZ());
		writeVarInt(packet.getHand(), out);
		out.writeBoolean(packet.isSneaking());
	}

	@Override
	public PacketInInteract createPacketData() {
		return new PacketInInteract();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInInteract.class);
	}

}
