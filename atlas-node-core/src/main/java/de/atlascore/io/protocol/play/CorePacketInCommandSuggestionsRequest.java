package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInCommandSuggestionsRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInCommandSuggestionsRequest implements PacketIO<PacketInCommandSuggestionsRequest> {

	@Override
	public void read(PacketInCommandSuggestionsRequest packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTransactionID(readVarInt(in));
		packet.setText(readString(in));
	}

	@Override
	public void write(PacketInCommandSuggestionsRequest packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getTransactionID(), out);
		writeString(packet.getText(), out);
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
