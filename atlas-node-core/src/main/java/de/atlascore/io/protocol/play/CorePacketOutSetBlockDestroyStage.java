package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetBlockDestroyStage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetBlockDestroyStage implements PacketIO<PacketOutSetBlockDestroyStage> {

	@Override
	public void read(PacketOutSetBlockDestroyStage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.position = in.readLong();
		packet.stage = in.readByte();
	}

	@Override
	public void write(PacketOutSetBlockDestroyStage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeLong(packet.position);
		out.writeByte(packet.stage);
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
