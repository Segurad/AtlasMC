package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInLockDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketInLockDifficulty extends AbstractPacket implements PacketInLockDifficulty {

	public CorePacketInLockDifficulty() {
		super(0x11, CoreProtocolAdapter.VERSION);
	}

	private boolean locked;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		locked = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeBoolean(locked);
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

	
}
