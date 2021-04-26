package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;
import io.netty.buffer.ByteBuf;

public interface PacketOutEntityMetadata extends Packet {
	
	public int getEntityID();
	public ByteBuf getData();
	
	@Override
	public default int getDefaultID() {
		return 0x44;
	}

}
