package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSetSeenRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetSeenRecipe implements PacketIO<PacketInSetSeenRecipe> {

	@Override
	public void read(PacketInSetSeenRecipe packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.recipeID = readIdentifier(in);
	}

	@Override
	public void write(PacketInSetSeenRecipe packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeIdentifier(packet.recipeID, out);
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
