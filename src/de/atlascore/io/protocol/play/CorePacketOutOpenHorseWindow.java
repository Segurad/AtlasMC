package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutOpenHorseWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenHorseWindow extends PacketIO<PacketOutOpenHorseWindow> {

	@Override
	public void read(PacketOutOpenHorseWindow packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setSlots(readVarInt(in));
		packet.setEntityID(in.readInt());
	}

	@Override
	public void write(PacketOutOpenHorseWindow packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		writeVarInt(packet.getSlots(), out);
		out.writeInt(packet.getEntityID());
	}

	@Override
	public PacketOutOpenHorseWindow createPacketData() {
		return new PacketOutOpenHorseWindow();
	}

}
