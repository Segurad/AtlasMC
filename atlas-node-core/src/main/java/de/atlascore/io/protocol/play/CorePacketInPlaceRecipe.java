package de.atlascore.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.writeString;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlaceRecipe;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlaceRecipe implements PacketIO<PacketInPlaceRecipe> {

	@Override
	public void read(PacketInPlaceRecipe packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setRecipe(readString(in));
		packet.setMakeAll(in.readBoolean());
	}

	@Override
	public void write(PacketInPlaceRecipe packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getWindowID());
		writeString(packet.getRecipe(), out);
		out.writeBoolean(packet.getMakeAll());
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
