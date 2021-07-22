package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_OPEN_HORSE_WINDOW)
public interface PacketOutOpenHorseWindow extends PacketPlay, PacketOutbound {
	
	public byte getWindowID();
	public int getSlots();
	public int getEntityID();
	
	@Override
	default int getDefaultID() {
		return OUT_OPEN_HORSE_WINDOW;
	}

}
