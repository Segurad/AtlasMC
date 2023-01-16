package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInCraftRecipeRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInCraftRecipeRequest extends PacketIO<PacketInCraftRecipeRequest> {

	@Override
	public void read(PacketInCraftRecipeRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setRecipe(readString(in));
		packet.setMakeAll(in.readBoolean());
	}

	@Override
	public void write(PacketInCraftRecipeRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getWindowID());
		writeString(packet.getRecipe(), out);
		out.writeBoolean(packet.getMakeAll());
	}
	
	@Override
	public PacketInCraftRecipeRequest createPacketData() {
		return new PacketInCraftRecipeRequest();
	}
	
}
