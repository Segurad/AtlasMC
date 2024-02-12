package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutCommandSuggestionsResponse;
import de.atlasmc.io.protocol.play.PacketOutCommandSuggestionsResponse.Match;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCommandSuggestionsResponse implements PacketIO<PacketOutCommandSuggestionsResponse> {

	@Override
	public void read(PacketOutCommandSuggestionsResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTransactionID(readVarInt(in));
		packet.setStart(readVarInt(in));
		packet.setLength(readVarInt(in));
		int count = readVarInt(in);
		List<Match> matches = new ArrayList<>(count);
		while (count > 0) {
			String match = readString(in, 32767);
			boolean hasToolTip = in.readBoolean();
			String toolTip = null;
			if (hasToolTip) 
				toolTip = readString(in);
			matches.add(new Match(match, toolTip));
			count--;
		}
		packet.setMatches(matches);
	}

	@Override
	public void write(PacketOutCommandSuggestionsResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getTransactionID(), out);
		writeVarInt(packet.getStart(), out);
		writeVarInt(packet.getLength(), out);
		writeVarInt(packet.getMatches().size(), out);
		for (Match m : packet.getMatches()) {
			writeString(m.getMatch(), out);
			out.writeBoolean(m.hasToolTip());
			if (m.hasToolTip()) 
				writeString(m.getToolTip(), out);
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
