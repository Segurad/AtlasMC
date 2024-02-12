package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutOpenHorseScreen;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenHorseScreen implements PacketIO<PacketOutOpenHorseScreen> {

	@Override
	public void read(PacketOutOpenHorseScreen packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setSlots(readVarInt(in));
		packet.setEntityID(in.readInt());
	}

	@Override
	public void write(PacketOutOpenHorseScreen packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		writeVarInt(packet.getSlots(), out);
		out.writeInt(packet.getEntityID());
	}

	@Override
	public PacketOutOpenHorseScreen createPacketData() {
		return new PacketOutOpenHorseScreen();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutOpenHorseScreen.class);
	}

}
