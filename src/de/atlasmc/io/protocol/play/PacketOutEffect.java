package de.atlasmc.io.protocol.play;

import de.atlasmc.Effect;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_EFFECT)
public interface PacketOutEffect extends PacketPlay, PacketOutbound {
	
	public Effect getEffect();
	public long getPosition();
	public int getData();
	public boolean getDisableRelativVolume();
	
	@Override
	default int getDefaultID() {
		return OUT_EFFECT;
	}

}
