package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_REMOVE_ENTITY_EFFECT)
public class PacketOutRemoveEntityEffect extends AbstractPacket implements PacketPlayOut {
	
	private int entityID, effectID;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public int getEffectID() {
		return effectID;
	}
	
	public void setEffectID(int effectID) {
		this.effectID = effectID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_REMOVE_ENTITY_EFFECT;
	}

}
