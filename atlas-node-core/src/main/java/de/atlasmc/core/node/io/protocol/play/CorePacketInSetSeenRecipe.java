package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSetSeenRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetSeenRecipe implements PacketCodec<PacketInSetSeenRecipe> {

	@Override
	public void deserialize(PacketInSetSeenRecipe packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.recipeID = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
	}

	@Override
	public void serialize(PacketInSetSeenRecipe packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		NamespacedKey.STREAM_CODEC.serialize(packet.recipeID, out, null);
	}

	@Override
	public PacketInSetSeenRecipe createPacketData() {
		return new PacketInSetSeenRecipe();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetSeenRecipe.class);
	}
	
}
