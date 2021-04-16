package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetDifficulty;
import io.netty.buffer.ByteBuf;

public class PacketInSetDifficultyV1_16_3 extends AbstractPacket implements PacketInSetDifficulty {

	public PacketInSetDifficultyV1_16_3() {
		super(0x02, V1_16_3.version);
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
