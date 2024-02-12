package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetContainerSlot;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetContainerSlot implements PacketIO<PacketOutSetContainerSlot> {

	@Override
	public void read(PacketOutSetContainerSlot packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setStateID(readVarInt(in));
		packet.setSlot(in.readShort());
		packet.setItem(readSlot(in));
	}

	@Override
	public void write(PacketOutSetContainerSlot packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		writeVarInt(packet.getStateID(), out);
		out.writeShort(packet.getSlot());
		writeSlot(packet.getItem(), out);
	}

	@Override
	public PacketOutSetContainerSlot createPacketData() {
		return new PacketOutSetContainerSlot();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetContainerSlot.class);
	}

}
