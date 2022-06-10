package de.atlasmc.io.protocol.play;

import java.util.Collection;
import java.util.List;

import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_PROPERTIES)
public interface PacketOutEntityProperties extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public List<AttributeInstance> getAttributes();
	
	public void setEntity(int id);
	
	public void setAttributes(Collection<AttributeInstance> attributes);
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_PROPERTIES;
	}

}
