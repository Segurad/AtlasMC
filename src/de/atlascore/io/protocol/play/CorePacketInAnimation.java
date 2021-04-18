package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketInAnimation extends AbstractPacket implements PacketInAnimation {

	public CorePacketInAnimation() {
		super(0x2C, CoreProtocolAdapter.VERSION);
		
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
