package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerAbilities extends AbstractPacket implements PacketInPlayerAbilities {

	public CorePacketInPlayerAbilities() {
		super(CoreProtocolAdapter.VERSION);
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
	public byte getFlags() {
		return flags;
	}

	@Override
	public boolean isFlying() {
		return (flags & 0x02) == 0x02;
	}

}
