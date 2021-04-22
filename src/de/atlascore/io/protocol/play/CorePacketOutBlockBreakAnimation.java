package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.CoreBlockBreakAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockBreakAnimation extends AbstractPacket implements CoreBlockBreakAnimation {

	private int id, stage;
	private long pos;
	
	public CorePacketOutBlockBreakAnimation() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	/**
	 * 
	 * @param entityID the Entity's ID which is breaking the Block or a random number
	 * @param pos
	 * @param stage number between 0-9, any other value to remove it 
	 */
	public CorePacketOutBlockBreakAnimation(int entityID, long pos, int stage) {
		this();
		this.id = entityID;
		this.pos = pos;
		this.stage = stage;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = readVarInt(in);
		pos = in.readLong();
		stage = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(id, out);
		out.writeLong(pos);
		out.writeByte(stage);
	}

	@Override
	public int getEntityID() {
		return id;
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public int getStage() {
		return stage;
	}

}
