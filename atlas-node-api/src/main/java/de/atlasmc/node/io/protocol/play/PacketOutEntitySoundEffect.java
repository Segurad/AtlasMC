package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketSoundEffect;

@DefaultPacketID(packetID = PacketPlay.OUT_ENTITY_SOUND_EFFECT, definition = "sound_entity")
public class PacketOutEntitySoundEffect extends AbstractPacketSoundEffect implements PacketPlayOut {
	
	public int entityID;
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_SOUND_EFFECT;
	}

}
