package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketInAnimation extends PacketIO<PacketInAnimation> {
	
	@Override
	public void read(PacketInAnimation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setHand(readVarInt(in));
	}

	@Override
	public void write(PacketInAnimation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getHand(), out);
	}
	
	@Override
	public PacketInAnimation createPacketData() {
		return new PacketInAnimation();
	}

}
