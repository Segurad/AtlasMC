package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetBlockDestroyStage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetBlockDestroyStage implements PacketIO<PacketOutSetBlockDestroyStage> {

	@Override
	public void read(PacketOutSetBlockDestroyStage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setPosition(in.readLong());
		packet.setStage(in.readByte());
	}

	@Override
	public void write(PacketOutSetBlockDestroyStage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeLong(packet.getPosition());
		out.writeByte(packet.getStage());
	}

	@Override
	public PacketOutSetBlockDestroyStage createPacketData() {
		return new PacketOutSetBlockDestroyStage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetBlockDestroyStage.class);
	}

}
