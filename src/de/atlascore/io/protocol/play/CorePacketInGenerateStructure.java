package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInGenerateStructure;
import io.netty.buffer.ByteBuf;

public class CorePacketInGenerateStructure extends AbstractPacket implements PacketInGenerateStructure {

	public CorePacketInGenerateStructure() {
		super(CoreProtocolAdapter.VERSION);
	}

	private long loc;
	private int levels;
	private boolean keepJigsaws;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		loc = in.readLong();
		levels = readVarInt(in);
		keepJigsaws = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(loc);
		writeVarInt(levels, out);
		out.writeBoolean(keepJigsaws);
	}

	@Override
	public long getPosition() {
		return loc;
	}

	@Override
	public int getLevels() {
		return levels;
	}

	@Override
	public boolean getKeepJigsaws() {
		return keepJigsaws;
	}

}
