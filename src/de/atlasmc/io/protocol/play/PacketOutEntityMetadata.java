package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import io.netty.buffer.ByteBuf;

@DefaultPacketID(PacketPlay.OUT_ENTITY_METADATA)
public interface PacketOutEntityMetadata extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public ByteBuf getData();
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_METADATA;
	}

}
