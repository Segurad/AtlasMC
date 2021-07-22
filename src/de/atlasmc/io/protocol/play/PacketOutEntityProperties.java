package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_PROPERTIES)
public interface PacketOutEntityProperties extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public List<AttributeInstance> getAttributes();
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_PROPERTIES;
	}

}
