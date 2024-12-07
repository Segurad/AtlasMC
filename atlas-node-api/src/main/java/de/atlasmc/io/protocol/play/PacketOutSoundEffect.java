package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketSoundEffect;

@DefaultPacketID(PacketPlay.OUT_SOUND_EFFECT)
public class PacketOutSoundEffect extends AbstractPacketSoundEffect implements PacketPlayOut {
	
	public double x;
	public double y;
	public double z;
	
	@Override
	public int getDefaultID() {
		return OUT_SOUND_EFFECT;
	}

}
