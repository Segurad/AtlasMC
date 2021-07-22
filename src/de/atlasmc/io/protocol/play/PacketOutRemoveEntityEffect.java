package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_REMOVE_ENTITY_EFFECT)
public interface PacketOutRemoveEntityEffect extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public int getEffectID();
	
	@Override
	default int getDefaultID() {
		return OUT_REMOVE_ENTITY_EFFECT;
	}

}
