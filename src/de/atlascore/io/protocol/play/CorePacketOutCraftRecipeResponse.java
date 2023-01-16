package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutCraftRecipeResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCraftRecipeResponse extends PacketIO<PacketOutCraftRecipeResponse> {

	@Override
	public void read(PacketOutCraftRecipeResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setRecipe(readString(in));
	}

	@Override
	public void write(PacketOutCraftRecipeResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		writeString(packet.getRecipe(), out);
	}
	
	@Override
	public PacketOutCraftRecipeResponse createPacketData() {
		return new PacketOutCraftRecipeResponse();
	}

}
