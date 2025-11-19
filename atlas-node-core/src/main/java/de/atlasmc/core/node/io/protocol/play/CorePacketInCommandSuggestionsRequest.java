package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInCommandSuggestionsRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInCommandSuggestionsRequest implements PacketIO<PacketInCommandSuggestionsRequest> {

	@Override
	public void read(PacketInCommandSuggestionsRequest packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.transactionID = readVarInt(in);
		packet.text = StringCodec.readString(in);
	}

	@Override
	public void write(PacketInCommandSuggestionsRequest packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.transactionID, out);
		StringCodec.writeString(packet.text, out);
	}
	
	@Override
	public PacketInCommandSuggestionsRequest createPacketData() {
		return new PacketInCommandSuggestionsRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInCommandSuggestionsRequest.class);
	}
	
}
