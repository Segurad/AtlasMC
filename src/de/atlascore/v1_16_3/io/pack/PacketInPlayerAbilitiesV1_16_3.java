package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInPlayerAbilities;

public class PacketInPlayerAbilitiesV1_16_3 extends AbstractPacket implements PacketInPlayerAbilities {

	public PacketInPlayerAbilitiesV1_16_3() {
		super(0x1A, V1_16_3.version);
	}
	
	private byte flags;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		flags = input.readByte();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public byte Flags() {
		return flags;
	}

}
