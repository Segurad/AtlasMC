package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInAnimation;
import io.netty.buffer.ByteBuf;

public class PacketInAnimationV1_16_3 extends AbstractPacket implements PacketInAnimation {

	public PacketInAnimationV1_16_3() {
		super(0x2C, V1_16_3.version);
		
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
