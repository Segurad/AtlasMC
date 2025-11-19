package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutPlaceGhostRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlaceGhostRecipe implements PacketIO<PacketOutPlaceGhostRecipe> {

	@Override
	public void read(PacketOutPlaceGhostRecipe packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.windowID = in.readByte();
		packet.recipeID = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
	}

	@Override
	public void write(PacketOutPlaceGhostRecipe packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.windowID);
		NamespacedKey.STREAM_CODEC.serialize(packet.recipeID, out, null);
	}

	@Override
	public PacketOutPlaceGhostRecipe createPacketData() {
		return new PacketOutPlaceGhostRecipe();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPlaceGhostRecipe.class);
	}

}
