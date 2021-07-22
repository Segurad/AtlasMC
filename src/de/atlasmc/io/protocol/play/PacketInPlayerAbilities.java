package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_PLAYER_ABILITIES)
public interface PacketInPlayerAbilities extends PacketPlay, PacketInbound {
	
	public byte getFlags();
	
	@Override
	default int getDefaultID() {
		return IN_PLAYER_ABILITIES;
	}

}
