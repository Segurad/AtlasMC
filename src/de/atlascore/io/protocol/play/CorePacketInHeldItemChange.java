package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInHeldItemChange;
import io.netty.buffer.ByteBuf;

public class CorePacketInHeldItemChange extends PacketIO<PacketInHeldItemChange> {

	@Override
	public void read(PacketInHeldItemChange packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setSlot(in.readShort());
	}

	@Override
	public void write(PacketInHeldItemChange packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeShort(packet.getSlot());
	}

	@Override
	public PacketInHeldItemChange createPacketData() {
		return new PacketInHeldItemChange();
	}

}
