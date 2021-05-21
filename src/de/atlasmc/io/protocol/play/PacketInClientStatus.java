package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInClientStatus extends Packet {
	
	public StatusAction getAction();
	
	@Override
	default int getDefaultID() {
		return 0x04;
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
