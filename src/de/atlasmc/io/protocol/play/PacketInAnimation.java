package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_ANIMATION)
public interface PacketInAnimation extends PacketPlay, PacketInbound {
	
	public int getHand();
	
	@Override
	public default int getDefaultID() {
		return IN_ANIMATION;
	}

}
