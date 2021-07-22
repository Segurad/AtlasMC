package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CLICK_WINDOW_BUTTON)
public interface PacketInClickWindowButton extends PacketPlay, PacketInbound {

	public byte getWindowID();
	public byte getButtonID();
	
	@Override
	default int getDefaultID() {
		return IN_CLICK_WINDOW_BUTTON;
	}
}
