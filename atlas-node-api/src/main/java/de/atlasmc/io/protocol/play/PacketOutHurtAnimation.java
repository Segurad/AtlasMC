package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_HURT_ANIMATION)
public class PacketOutHurtAnimation extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public float yaw;
	
	@Override
	public int getDefaultID() {
		return OUT_HURT_ANIMATION;
	}

}
