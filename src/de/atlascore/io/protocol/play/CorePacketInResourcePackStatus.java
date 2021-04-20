package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInResourcePackStatus;
import io.netty.buffer.ByteBuf;

public class CorePacketInResourcePackStatus extends AbstractPacket implements PacketInResourcePackStatus {

	public CorePacketInResourcePackStatus() {
		super(0x21, CoreProtocolAdapter.VERSION);
	}

	public int result;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		result = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(result, out);
	}

	@Override
	public int getResult() {
		return result;
	}

}
