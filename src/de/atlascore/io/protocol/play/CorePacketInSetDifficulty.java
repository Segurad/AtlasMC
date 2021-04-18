package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetDifficulty extends AbstractPacket implements PacketInSetDifficulty {

	public CorePacketInSetDifficulty() {
		super(0x02, CoreProtocolAdapter.VERSION);
	}

	private int dif;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		dif = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(dif);
	}

	@Override
	public int getDifficulty() {
		return dif;
	}

}
