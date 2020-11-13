package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInUseItem;

public class PacketInUseItemV1_16_3 extends AbstractPacket implements PacketInUseItem {

	public PacketInUseItemV1_16_3() {
		super(0x2F, V1_16_3.version);
	}
	
	private int hand;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		hand = readVarInt(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int Hand() {
		return hand;
	}

}
