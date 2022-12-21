package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetSlot extends CoreAbstractHandler<PacketOutSetSlot> {

	@Override
	public void read(PacketOutSetSlot packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setSlot(in.readShort());
		packet.setItem(readSlot(in));
	}

	@Override
	public void write(PacketOutSetSlot packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		out.writeShort(packet.getSlot());
		writeSlot(packet.getItem(), out);
	}

	@Override
	public PacketOutSetSlot createPacketData() {
		return new PacketOutSetSlot();
	}

}
