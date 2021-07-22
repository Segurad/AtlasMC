package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_OPEN_SIGN_EDITOR)
public interface PacketOutOpenSignEditor extends PacketPlay, PacketOutbound {
	
	public long getPosition();
	
	@Override
	default int getDefaultID() {
		return OUT_OPEN_SIGN_EDITOR;
	}

}
