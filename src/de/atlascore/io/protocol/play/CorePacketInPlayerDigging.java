package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerDigging;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerDigging extends AbstractPacket implements PacketInPlayerDigging {

	public CorePacketInPlayerDigging() {
		super(0x1B, CoreProtocolAdapter.VERSION);
	}

	private int status;
	private SimpleLocation pos;
	private byte face;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		status = readVarInt(in);
		pos = readPosition(in);
		face = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(status, out);
		writePosition(pos, out);
		out.writeByte(face);
	}

	@Override
	public int Status() {
		return status;
	}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public byte Face() {
		return face;
	}
	
	

}
