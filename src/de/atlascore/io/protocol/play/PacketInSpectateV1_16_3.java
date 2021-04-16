package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSpectate;
import io.netty.buffer.ByteBuf;

public class PacketInSpectateV1_16_3 extends AbstractPacket implements PacketInSpectate {

	public PacketInSpectateV1_16_3() {
		super(0x2D, V1_16_3.version);
	}
	
	private String uuid;

	@Override
	public void read(ByteBuf in) throws IOException {
		uuid = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(uuid, out);
	}

	@Override
	public String UUID() {
		return uuid;
	}

}
