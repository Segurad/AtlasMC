package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.component.FinalComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutTabComplete;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTabComplete extends AbstractPacket implements PacketOutTabComplete {

	private int transactionID, start, length;
	private List<Match> matches;
	
	public CorePacketOutTabComplete() {
		super(0x0F, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutTabComplete(int transactionID, int start, int length, List<Match> matches) {
		this();
		this.transactionID = transactionID;
		this.start = start;
		this.length = length;
		this.matches = matches;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		transactionID = readVarInt(in);
		start = readVarInt(in);
		length = readVarInt(in);
		int count = readVarInt(in);
		matches = new ArrayList<>(count);
		while (count > 0) {
			String s = readString(in);
			boolean ht = in.readBoolean();
			String tt = null;
			if (ht) tt = readString(in);
			matches.add(new Match(s, new FinalComponent(tt, null)));
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		if (matches == null) return;
		writeVarInt(transactionID, out);
		writeVarInt(start, out);
		writeVarInt(length, out);
		writeVarInt(matches.size(), out);
		for (Match m : matches) {
			writeString(m.getMatch(), out);
			out.writeBoolean(m.hasToolTip());
			if (m.hasToolTip()) writeString(m.getToolTip().getJsonText(), out);
		}
	}

}
