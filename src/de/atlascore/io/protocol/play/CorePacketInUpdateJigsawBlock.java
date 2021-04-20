package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateJigsawBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateJigsawBlock extends AbstractPacket implements PacketInUpdateJigsawBlock {

	public CorePacketInUpdateJigsawBlock() {
		super(0x28, CoreProtocolAdapter.VERSION);
	}
	
	private long pos;
	private String name,target,pool,finalstate,jointtype;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		name = readString(in);
		target = readString(in);
		pool = readString(in);
		finalstate = readString(in);
		jointtype = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
		writeString(name, out);
		writeString(target, out);
		writeString(pool, out);
		writeString(finalstate, out);
		writeString(jointtype, out);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public String getPool() {
		return pool;
	}

	@Override
	public String getFinalState() {
		return finalstate;
	}

	@Override
	public String getJointtype() {
		return jointtype;
	}

}
