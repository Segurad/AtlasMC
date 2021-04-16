package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInLockDifficulty;
import io.netty.buffer.ByteBuf;

public class PacketInLockDifficultyV1_16_3 extends AbstractPacket implements PacketInLockDifficulty {

	public PacketInLockDifficultyV1_16_3() {
		super(0x11, V1_16_3.version);
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
	public boolean Locked() {
		return locked;
	}

	
}
