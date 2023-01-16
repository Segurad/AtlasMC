package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPluginMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketInPluginMessage extends PacketIO<PacketInPluginMessage> {
	
	@Override
	public void read(PacketInPluginMessage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setChannel(readString(in));
		byte[] data = new byte[in.readableBytes()];
		in.readBytes(data);
		packet.setData(data);
	}

	@Override
	public void write(PacketInPluginMessage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getChannel(), out);
		out.writeBytes(packet.getData());
	}

	@Override
	public PacketInPluginMessage createPacketData() {
		return new PacketInPluginMessage();
	}

}
