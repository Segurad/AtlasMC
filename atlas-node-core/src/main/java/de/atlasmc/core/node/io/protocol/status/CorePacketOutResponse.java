package de.atlasmc.core.node.io.protocol.status;

import java.io.IOException;
import java.io.StringReader;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.status.ClientboundResponse;
import de.atlasmc.util.configuration.file.JsonConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResponse implements PacketCodec<ClientboundResponse> {

	@Override
	public void deserialize(ClientboundResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		String rawResponse = StringCodec.readString(in);
		packet.response = JsonConfiguration.loadConfiguration(new StringReader(rawResponse));
	}

	@Override
	public void serialize(ClientboundResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		String rawResponse = packet.response.saveToString();
		StringCodec.writeString(rawResponse, out);
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
