package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutBlockUpdate;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockUpdate implements PacketIO<PacketOutBlockUpdate> {
	
	@Override
	public void read(PacketOutBlockUpdate packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.blockStateID = readVarInt(in);
	}

	@Override
	public void write(PacketOutBlockUpdate packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		writeVarInt(packet.blockStateID, out);
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
