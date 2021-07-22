package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SOUND_EFFECT)
public interface PacketOutSoundEffect extends PacketPlay, PacketOutbound {
	
	public int getSoundID();
	public SoundCategory getCategory();
	public double getX();
	public double getY();
	public double getZ();
	public float getValue();
	public float getPitch();
	
	@Override
	public default int getDefaultID() {
		return OUT_SOUND_EFFECT;
	}

}
