package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.io.Packet;

public interface PacketOutEntityProperties extends Packet {
	
	public int getEntityID();
	public List<AttributeInstance> getAttributes();
	
	@Override
	public default int getDefaultID() {
		return 0x58;
	}

}
