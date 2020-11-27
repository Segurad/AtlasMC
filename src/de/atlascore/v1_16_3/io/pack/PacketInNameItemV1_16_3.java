package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInNameItem;

public class PacketInNameItemV1_16_3 extends AbstractPacket implements PacketInNameItem {

	public PacketInNameItemV1_16_3() {
		super(0x20, V1_16_3.version);
	}

	private String itemname;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		itemname = readString(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public String ItemName() {
		return itemname;
	}

}
