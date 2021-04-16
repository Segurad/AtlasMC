package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUseItem;
import io.netty.buffer.ByteBuf;

public class PacketInUseItemV1_16_3 extends AbstractPacket implements PacketInUseItem {

	public PacketInUseItemV1_16_3() {
		super(0x2F, V1_16_3.version);
	}
	
	private int hand;

	@Override
	public void read(ByteBuf in) throws IOException {
		hand = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(hand, out);
	}

	@Override
	public int Hand() {
		return hand;
	}

}
