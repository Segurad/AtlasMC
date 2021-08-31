package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Difficulty;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutServerDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketOutServerDifficulty extends AbstractPacket implements PacketOutServerDifficulty {

	private int difficulty;
	private boolean locked;
	
	public CorePacketOutServerDifficulty() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutServerDifficulty(Difficulty difficulty, boolean locked) {
		this();
		this.difficulty = difficulty.ordinal();
		this.locked = locked;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		difficulty = in.readUnsignedByte();
		locked = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(difficulty);
		out.writeBoolean(locked);
	}

	@Override
	public Difficulty getDifficulty() {
		switch(difficulty) {
		case 0: return Difficulty.PEACEFUL;
		case 1: return Difficulty.EASY;
		case 2: return Difficulty.NORAML;
		case 3: return Difficulty.HARD;
		default: return Difficulty.PEACEFUL;
		}
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

}
