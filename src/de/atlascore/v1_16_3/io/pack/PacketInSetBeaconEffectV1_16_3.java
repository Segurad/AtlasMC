package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInSetBeaconEffect;

public class PacketInSetBeaconEffectV1_16_3 extends AbstractPacket implements PacketInSetBeaconEffect {

	public PacketInSetBeaconEffectV1_16_3() {
		super(0x24, V1_16_3.version);
	}

	private int primeffect,secondeffect;
	
	@Override
	public void read(int length, DataInputStream input) throws IOException {
		primeffect = readVarInt(input);
		secondeffect = readVarInt(input);	
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int PrimaryEffect() {
		return primeffect;
	}

	@Override
	public int SecondaryEffect() {
		return secondeffect;
	}

}
