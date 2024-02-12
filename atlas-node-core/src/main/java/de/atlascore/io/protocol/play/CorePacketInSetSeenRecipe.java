package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetSeenRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetSeenRecipe implements PacketIO<PacketInSetSeenRecipe> {

	@Override
	public void read(PacketInSetSeenRecipe packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setRecipeID(readString(in));
	}

	@Override
	public void write(PacketInSetSeenRecipe packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getRecipeID(), out);
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
