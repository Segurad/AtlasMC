package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	public void read(int length, DataInputStream input) throws IOException {
		itemname = readString(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public String ItemName() {
		return itemname;
	}

}
