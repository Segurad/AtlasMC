package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutRemoveEntityEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRemoveEntityEffect implements PacketIO<PacketOutRemoveEntityEffect> {

	@Override
	public void read(PacketOutRemoveEntityEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setEffectID(readVarInt(in));
	}

	@Override
	public void write(PacketOutRemoveEntityEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		writeVarInt(packet.getEffectID(), out);
	}

	@Override
	public PacketOutRemoveEntityEffect createPacketData() {
		return new PacketOutRemoveEntityEffect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRemoveEntityEffect.class);
	}

}
