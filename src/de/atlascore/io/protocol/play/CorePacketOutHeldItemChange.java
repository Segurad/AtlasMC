package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutHeldItemChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutHeldItemChange extends CoreAbstractHandler<PacketOutHeldItemChange> {

	@Override
	public void read(PacketOutHeldItemChange packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setSlot(in.readByte());
	}

	@Override
	public void write(PacketOutHeldItemChange packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getSlot());
	}

	@Override
	public PacketOutHeldItemChange createPacketData() {
		return new PacketOutHeldItemChange();
	}

}
