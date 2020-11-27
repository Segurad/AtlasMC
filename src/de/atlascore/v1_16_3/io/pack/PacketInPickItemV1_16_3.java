package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInPickItem;

public class PacketInPickItemV1_16_3 extends AbstractPacket implements PacketInPickItem {

	public PacketInPickItemV1_16_3() {
		super(0x18, V1_16_3.version);
		
	}

	private int slottouse;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		slottouse = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int SlotToUse() {
		return slottouse;
	}

}
