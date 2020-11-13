package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInSelectTrade;

public class PacketInSelectTradeV1_16_3 extends AbstractPacket implements PacketInSelectTrade {

	public PacketInSelectTradeV1_16_3() {
		super(0x23, V1_16_3.version);
	}
	
	private int selectedslot;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		selectedslot = readVarInt(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int SelectedSlot() {
		return selectedslot;
	}

}
