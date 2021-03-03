package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUseItem;

public class PacketInUseItemV1_16_3 extends AbstractPacket implements PacketInUseItem {

	public PacketInUseItemV1_16_3() {
		super(0x2F, V1_16_3.version);
	}
	
	private int hand;

	@Override
	public void read(int length, DataInput input) throws IOException {
		hand = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int Hand() {
		return hand;
	}

}
