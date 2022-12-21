package de.atlasmc.io.protocol.play;

import de.atlasmc.entity.Entity.Animation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ENTITY_ANIMATION)
public class PacketOutEntityAnimation extends AbstractPacket implements PacketPlayOut {
	
	private Animation animation;
	private int entityID;
	
	public Animation getAnimation() {
		return animation;
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_ANIMATION;
	}

}
