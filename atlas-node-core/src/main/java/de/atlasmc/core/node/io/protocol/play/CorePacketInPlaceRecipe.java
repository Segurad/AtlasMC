package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.writeIdentifier;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInPlaceRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlaceRecipe implements PacketIO<PacketInPlaceRecipe> {

	@Override
	public void read(PacketInPlaceRecipe packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.windowID = in.readByte();
		packet.recipe = readIdentifier(in);
		packet.makeAll = in.readBoolean();
	}

	@Override
	public void write(PacketInPlaceRecipe packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.windowID);
		writeIdentifier(packet.recipe, out);
		out.writeBoolean(packet.makeAll);
	}
	
	@Override
	public PacketInPlaceRecipe createPacketData() {
		return new PacketInPlaceRecipe();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlaceRecipe.class);
	}
	
}
