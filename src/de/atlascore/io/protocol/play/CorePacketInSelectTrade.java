package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSelectTrade;
import io.netty.buffer.ByteBuf;

public class CorePacketInSelectTrade extends AbstractPacket implements PacketInSelectTrade {

	public CorePacketInSelectTrade() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	private int selectedslot;

	@Override
	public void read(ByteBuf in) throws IOException {
		selectedslot = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(selectedslot, out);
	}

	@Override
	public int getSelectedSlot() {
		return selectedslot;
	}

}
