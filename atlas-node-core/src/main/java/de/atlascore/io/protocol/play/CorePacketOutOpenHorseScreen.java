package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutOpenHorseScreen;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenHorseScreen implements PacketIO<PacketOutOpenHorseScreen> {

	@Override
	public void read(PacketOutOpenHorseScreen packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = in.readByte();
		packet.slots = readVarInt(in);
		packet.entityID = in.readInt();
	}

	@Override
	public void write(PacketOutOpenHorseScreen packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.windowID);
		writeVarInt(packet.slots, out);
		out.writeInt(packet.entityID);
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
