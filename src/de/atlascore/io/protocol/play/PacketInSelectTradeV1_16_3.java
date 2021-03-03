package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSelectTrade;

public class PacketInSelectTradeV1_16_3 extends AbstractPacket implements PacketInSelectTrade {

	public PacketInSelectTradeV1_16_3() {
		super(0x23, V1_16_3.version);
	}
	
	private int selectedslot;

	@Override
	public void read(int length, DataInput input) throws IOException {
		selectedslot = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int SelectedSlot() {
		return selectedslot;
	}

}
