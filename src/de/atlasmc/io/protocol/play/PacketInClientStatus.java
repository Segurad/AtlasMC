package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CLIENT_STATUS)
public interface PacketInClientStatus extends PacketPlay, PacketInbound {
	
	public StatusAction getAction();
	
	@Override
	default int getDefaultID() {
		return IN_CLIENT_STATUS;
	}
	
	public static enum StatusAction {
		RESPAWN,
		STATS;
		
		public static StatusAction getByID(int id) {
			if (id == 0) {
				return RESPAWN;
			} else if (id == 1) {
				return STATS;
			}
			return null;
		}
	}

}
