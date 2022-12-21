package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutPluginMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPluginMessage extends CoreAbstractHandler<PacketOutPluginMessage> {

	@Override
	public void read(PacketOutPluginMessage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setIdentifier(readString(in));
		byte[] data = new byte[in.readableBytes()];
		in.readBytes(data);
		packet.setData(data);
	}

	@Override
	public void write(PacketOutPluginMessage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getIdentifier(), out);
		out.writeBytes(packet.getData());
	}

	@Override
	public PacketOutPluginMessage createPacketData() {
		return new PacketOutPluginMessage();
	}

}
