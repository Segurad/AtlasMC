package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutBlockUpdate;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockUpdate implements PacketIO<PacketOutBlockUpdate> {
	
	@Override
	public void read(PacketOutBlockUpdate packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setBlockStateID(readVarInt(in));
	}

	@Override
	public void write(PacketOutBlockUpdate packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getBlockStateID(), out);
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
