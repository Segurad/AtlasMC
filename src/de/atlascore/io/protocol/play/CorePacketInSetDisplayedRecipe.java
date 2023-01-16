package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetDisplayedRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetDisplayedRecipe extends PacketIO<PacketInSetDisplayedRecipe> {

	@Override
	public void read(PacketInSetDisplayedRecipe packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setRecipeID(readString(in));
	}

	@Override
	public void write(PacketInSetDisplayedRecipe packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getRecipeID(), out);
	}

	@Override
	public PacketInSetDisplayedRecipe createPacketData() {
		return new PacketInSetDisplayedRecipe();
	}
	
}
