package de.atlasmc.io.handshake;

import java.io.IOException;

import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;

public class PacketAtlasNodeHandshake extends AbstractPacket {

	public PacketAtlasNodeHandshake(int id, int version) {
		super(id, version);
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

	@Override
	public int getDefaultID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
