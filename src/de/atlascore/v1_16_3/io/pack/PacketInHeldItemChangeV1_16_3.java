package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInHeldItemChange;

public class PacketInHeldItemChangeV1_16_3 extends AbstractPacket implements PacketInHeldItemChange {

	public PacketInHeldItemChangeV1_16_3() {
		super(0x25, V1_16_3.version);
	}
	
	private short slot;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		slot = input.readShort();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public short Slot() {
		return slot;
	}

}
