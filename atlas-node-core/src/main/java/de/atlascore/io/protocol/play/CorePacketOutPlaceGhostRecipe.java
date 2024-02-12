package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPlaceGhostRecipe;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutPlaceGhostRecipe implements PacketIO<PacketOutPlaceGhostRecipe> {

	@Override
	public void read(PacketOutPlaceGhostRecipe packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.windowID = in.readByte();
		packet.recipeID = readIdentifier(in);
	}

	@Override
	public void write(PacketOutPlaceGhostRecipe packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.windowID);
		writeIdentifier(packet.recipeID, out);
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
