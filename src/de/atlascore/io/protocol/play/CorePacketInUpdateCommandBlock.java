package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateCommandBlock extends AbstractPacket implements PacketInUpdateCommandBlock {

	public CorePacketInUpdateCommandBlock() {
		super(0x26, CoreProtocolAdapter.VERSION);
	}
	
	private long pos;
	private String cmd;
	private int mode;
	private byte flags;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		cmd = readString(in);
		mode = readVarInt(in);
		flags = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
		writeString(cmd, out);
		writeVarInt(mode, out);
		out.writeByte(mode);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public String getCommand() {
		return cmd;
	}

	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public byte Flags() {
		return flags;
	}

}
