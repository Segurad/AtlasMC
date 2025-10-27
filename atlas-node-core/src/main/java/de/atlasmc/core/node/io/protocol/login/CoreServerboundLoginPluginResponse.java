package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ServerboundLoginPluginResponse;
import io.netty.buffer.ByteBuf;

public class CoreServerboundLoginPluginResponse implements PacketIO<ServerboundLoginPluginResponse> {

	@Override
	public void read(ServerboundLoginPluginResponse packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.messageID = readVarInt(in);
		if (in.readBoolean()) {
			int size = readArrayLength(in, 1048576);
			packet.data = in.readBytes(size);
		}
	}

	@Override
	public void write(ServerboundLoginPluginResponse packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.messageID, out);
		if (packet.data == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			writeVarInt(packet.data.readableBytes(), out);
			out.writeBytes(packet.data);
		}
	}

	@Override
	public ServerboundLoginPluginResponse createPacketData() {
		return new ServerboundLoginPluginResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundLoginPluginResponse.class);
	}

}
