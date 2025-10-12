package de.atlasmc.core.node.io.protocol.status;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.io.StringReader;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.status.ClientboundResponse;
import de.atlasmc.util.configuration.file.JsonConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResponse implements PacketIO<ClientboundResponse> {

	@Override
	public void read(ClientboundResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		String rawResponse = readString(in);
		packet.response = JsonConfiguration.loadConfiguration(new StringReader(rawResponse));
	}

	@Override
	public void write(ClientboundResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		String rawResponse = packet.response.saveToString();
		writeString(rawResponse, out);
	}
	
	@Override
	public ClientboundResponse createPacketData() {
		return new ClientboundResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundResponse.class);
	}

}
