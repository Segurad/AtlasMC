package de.atlasmc.node.io.protocol.play;

import de.atlasmc.IDHolder;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CLIENT_COMMAND, definition = "client_command")
public class PacketInClientCommand extends AbstractPacket implements PacketPlayIn {
	
	public StatusAction action;
	
	@Override
	public int getDefaultID() {
		return IN_CLIENT_COMMAND;
	}
	
	public static enum StatusAction implements IDHolder {
		
		RESPAWN,
		STATS;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
