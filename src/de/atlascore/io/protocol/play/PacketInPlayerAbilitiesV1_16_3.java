package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class PacketInPlayerAbilitiesV1_16_3 extends AbstractPacket implements PacketInPlayerAbilities {

	public PacketInPlayerAbilitiesV1_16_3() {
		super(0x1A, V1_16_3.version);
	}
	
	private byte flags;

	@Override
	public void read(ByteBuf in) throws IOException {
		flags = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(flags);
	}	

	@Override
	public byte Flags() {
		return flags;
	}

}
