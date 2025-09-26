package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateRecipes;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateRecipes implements PacketIO<PacketOutUpdateRecipes> {

	@Override
	public void read(PacketOutUpdateRecipes packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		// TODO waiting for definition
	}

	@Override
	public void write(PacketOutUpdateRecipes packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		// TODO waiting for definition
	}

	@Override
	public PacketOutUpdateRecipes createPacketData() {
		return new PacketOutUpdateRecipes();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateRecipes.class);
	}

}
