package de.atlasmc.io.handshake;

import java.io.IOException;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import io.netty.buffer.ByteBuf;

@DefaultPacketID(0xFE)
public class PacketMinecraftLegacyHandshake extends AbstractPacket {

	public PacketMinecraftLegacyHandshake(int id, int version) {
		super(0xFE, version);
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
