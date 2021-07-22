package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_BOSS_BAR)
public interface PacketOutCoreBossBar extends PacketPlay, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_BOSS_BAR;
	}
	
	public static enum BossBarAction {
		ADD,
		REMOVE,
		UPDATE_HEALTH,
		UPDATE_TITLE,
		UPDATE_STYLE,
		UPDATE_FLAGS
	}

}
