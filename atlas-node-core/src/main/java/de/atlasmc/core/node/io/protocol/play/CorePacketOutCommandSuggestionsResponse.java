package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutCommandSuggestionsResponse;
import de.atlasmc.node.io.protocol.play.PacketOutCommandSuggestionsResponse.Match;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCommandSuggestionsResponse implements PacketIO<PacketOutCommandSuggestionsResponse> {

	@Override
	public void read(PacketOutCommandSuggestionsResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.transactionID = readVarInt(in);
		packet.start = readVarInt(in);
		packet.length = readVarInt(in);
		int count = readVarInt(in);
		List<Match> matches = new ArrayList<>(count);
		while (count > 0) {
			String match = StringCodec.readString(in);
			boolean hasToolTip = in.readBoolean();
			String toolTip = null;
			if (hasToolTip) 
				toolTip = StringCodec.readString(in);
			matches.add(new Match(match, toolTip));
			count--;
		}
		packet.matches = matches;
	}

	@Override
	public void write(PacketOutCommandSuggestionsResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.transactionID, out);
		writeVarInt(packet.start, out);
		writeVarInt(packet.length, out);
		List<Match> matches = packet.matches;
		final int size = matches.size();
		writeVarInt(size, out);
		for (int i = 0; i < size; i++) {
			Match m = matches.get(i);
			StringCodec.writeString(m.getMatch(), out);
			String toolTip = m.getToolTip();
			if (toolTip != null) { 
				out.writeBoolean(true);
				StringCodec.writeString(m.getToolTip(), out);
			} else {
				out.writeBoolean(false);
			}
		}
	}
	
	@Override
	public PacketOutCommandSuggestionsResponse createPacketData() {
		return new PacketOutCommandSuggestionsResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutCommandSuggestionsResponse.class);
	}

}
