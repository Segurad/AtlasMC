package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CLOSE_WINDOW)
public interface PacketInCloseWindow extends PacketPlay, PacketInbound {
	
	public byte getWindowID();
	
	@Override
	default int getDefaultID() {
		return IN_CLOSE_WINDOW;
	}

}
