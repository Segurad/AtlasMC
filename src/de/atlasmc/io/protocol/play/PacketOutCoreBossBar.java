package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutCoreBossBar extends Packet {
	
	@Override
	default int getDefaultID() {
		return 0x0C;
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
