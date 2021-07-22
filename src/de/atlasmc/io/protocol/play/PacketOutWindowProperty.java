package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_WINDOW_PROPERTY)
public interface PacketOutWindowProperty extends PacketPlay, PacketOutbound {

	public byte getWindowID();
	public int getProperty();
	public int getValue();
	
	@Override
	default int getDefaultID() {
		return OUT_WINDOW_PROPERTY;
	}
	
}
