package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSelectTrade;
import io.netty.buffer.ByteBuf;

public class PacketInSelectTradeV1_16_3 extends AbstractPacket implements PacketInSelectTrade {

	public PacketInSelectTradeV1_16_3() {
		super(0x23, V1_16_3.version);
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
	public int SelectedSlot() {
		return selectedslot;
	}

}
