package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface CoreBossBar extends Packet {
	
	public static enum Action {
		ADD,
		REMOVE,
		UPDATE_HEALTH,
		UPDATE_TITLE,
		UPDATE_STYLE,
		UPDATE_FLAGS
	}

}
