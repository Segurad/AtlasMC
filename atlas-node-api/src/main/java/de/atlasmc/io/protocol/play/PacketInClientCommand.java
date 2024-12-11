package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CLIENT_COMMAND, definition = "client_command")
public class PacketInClientCommand extends AbstractPacket implements PacketPlayIn {
	
	public StatusAction action;
	
	@Override
	public int getDefaultID() {
		return IN_CLIENT_COMMAND;
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
