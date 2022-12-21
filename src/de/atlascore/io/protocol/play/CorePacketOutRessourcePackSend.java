package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutResourcePackSend;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRessourcePackSend extends CoreAbstractHandler<PacketOutResourcePackSend> {

	@Override
	public void read(PacketOutResourcePackSend packet, ByteBuf in, ConnectionHandler hander) throws IOException {
		packet.setURL(readString(in));
		packet.setHash(readString(in));
	}

	@Override
	public void write(PacketOutResourcePackSend packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getURL(), out);
		writeString(packet.getHash(), out);
	}

	@Override
	public PacketOutResourcePackSend createPacketData() {
		return new PacketOutResourcePackSend();
	}

}
