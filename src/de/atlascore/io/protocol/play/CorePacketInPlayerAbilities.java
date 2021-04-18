package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerAbilities extends AbstractPacket implements PacketInPlayerAbilities {

	public CorePacketInPlayerAbilities() {
		super(0x1A, CoreProtocolAdapter.VERSION);
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
