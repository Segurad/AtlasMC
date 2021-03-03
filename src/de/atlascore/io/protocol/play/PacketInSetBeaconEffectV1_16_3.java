package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetBeaconEffect;

public class PacketInSetBeaconEffectV1_16_3 extends AbstractPacket implements PacketInSetBeaconEffect {

	public PacketInSetBeaconEffectV1_16_3() {
		super(0x24, V1_16_3.version);
	}

	private int primeffect,secondeffect;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		primeffect = readVarInt(input);
		secondeffect = readVarInt(input);	
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int PrimaryEffect() {
		return primeffect;
	}

	@Override
	public int SecondaryEffect() {
		return secondeffect;
	}

}
