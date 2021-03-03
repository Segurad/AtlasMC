package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSpectate;

public class PacketInSpectateV1_16_3 extends AbstractPacket implements PacketInSpectate {

	public PacketInSpectateV1_16_3() {
		super(0x2D, V1_16_3.version);
	}
	
	private String uuid;

	@Override
	public void read(int length, DataInput input) throws IOException {
		uuid = readString(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public String UUID() {
		return uuid;
	}

}
