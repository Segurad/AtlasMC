package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_SET_BEACON_EFFECT)
public interface PacketInSetBeaconEffect extends PacketPlay, PacketInbound {
	
	public int getPrimaryEffect();
	public int getSecondaryEffect();
	
	@Override
	default int getDefaultID() {
		return IN_SET_BEACON_EFFECT;
	}

}
