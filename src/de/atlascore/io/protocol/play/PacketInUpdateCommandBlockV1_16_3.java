package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlock;
import io.netty.buffer.ByteBuf;

public class PacketInUpdateCommandBlockV1_16_3 extends AbstractPacket implements PacketInUpdateCommandBlock {

	public PacketInUpdateCommandBlockV1_16_3() {
		super(0x26, V1_16_3.version);
	}
	
	private SimpleLocation pos;
	private String cmd;
	private int mode;
	private byte flags;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = readPosition(in);
		cmd = readString(in);
		mode = readVarInt(in);
		flags = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writePosition(pos, out);
		writeString(cmd, out);
		writeVarInt(mode, out);
		out.writeByte(mode);
	}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public String Command() {
		return cmd;
	}

	@Override
	public int Mode() {
		return mode;
	}

	@Override
	public byte Flags() {
		return flags;
	}

}
