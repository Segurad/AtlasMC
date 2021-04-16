package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetBeaconEffect;
import io.netty.buffer.ByteBuf;

public class PacketInSetBeaconEffectV1_16_3 extends AbstractPacket implements PacketInSetBeaconEffect {

	public PacketInSetBeaconEffectV1_16_3() {
		super(0x24, V1_16_3.version);
	}

	private int primeffect,secondeffect;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		primeffect = readVarInt(in);
		secondeffect = readVarInt(in);	
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(primeffect, out);
		writeVarInt(secondeffect, out);
	}

	@Override
	public int PrimaryEffect() {
		return primeffect;
	}

	@Override
	public int SecondaryEffect() {
		return secondeffect;
	}

}
