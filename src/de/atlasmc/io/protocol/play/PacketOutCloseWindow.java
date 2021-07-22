package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_CLOSE_WINDOW)
public interface PacketOutCloseWindow extends PacketPlay, PacketOutbound {
	
	public byte getWindowID();
	
	@Override
	default int getDefaultID() {
		return OUT_CLOSE_WINDOW;
	}

}
