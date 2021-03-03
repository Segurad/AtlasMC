package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInLockDifficulty;

public class PacketInLockDifficultyV1_16_3 extends AbstractPacket implements PacketInLockDifficulty {

	public PacketInLockDifficultyV1_16_3() {
		super(0x11, V1_16_3.version);
	}

	private boolean locked;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		locked = input.readBoolean();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public boolean Locked() {
		return locked;
	}

	
}
