package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketInLoginPluginResponse;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketInLoginPluginResponse implements PacketIO<PacketInLoginPluginResponse> {

	@Override
	public void read(PacketInLoginPluginResponse packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.messageID = readVarInt(in);
		packet.successful = in.readBoolean();
		if (packet.successful) {
			packet.data = in.readBytes(in.readableBytes());
		}
	}

	@Override
	public void write(PacketInLoginPluginResponse packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.messageID, out);
		out.writeBoolean(packet.successful);
		if (packet.successful) {
			out.writeBytes(packet.data);
		}
	}

	@Override
	public PacketInLoginPluginResponse createPacketData() {
		return new PacketInLoginPluginResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInLoginPluginResponse.class);
	}

}
