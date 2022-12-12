package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CLIENT_STATUS)
public class PacketInClientStatus extends AbstractPacket implements PacketPlayIn {
	
	private StatusAction action;
	
	public StatusAction getAction() {
		return action;
	}
	
	public void setAction(StatusAction action) {
		this.action = action;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CLIENT_STATUS;
	}
	
	public static enum StatusAction {
		RESPAWN,
		STATS;
		
		public int getID() {
			return ordinal();
		}
		
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
