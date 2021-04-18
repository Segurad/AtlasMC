package de.atlasmc.io.handshake;

import java.io.IOException;

import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;

public class PacketAtlasPlayerHandshake extends AbstractPacket {

	public PacketAtlasPlayerHandshake() {
		super(0x01, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
