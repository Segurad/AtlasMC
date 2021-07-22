package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SET_COOLDOWN)
public interface PacketOutSetCooldown extends PacketPlay, PacketOutbound {
	
	public int getItemID();
	public int getCooldown();
	
	@Override
	default int getDefaultID() {
		return OUT_SET_COOLDOWN;
	}

}
