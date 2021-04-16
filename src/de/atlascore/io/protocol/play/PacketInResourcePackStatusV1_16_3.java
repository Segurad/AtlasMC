package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInResourcePackStatus;
import io.netty.buffer.ByteBuf;

public class PacketInResourcePackStatusV1_16_3 extends AbstractPacket implements PacketInResourcePackStatus {

	public PacketInResourcePackStatusV1_16_3() {
		super(0x21, V1_16_3.version);
	}

	public int result;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		result = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(result, out);
	}

	@Override
	public int Result() {
		return result;
	}

}
