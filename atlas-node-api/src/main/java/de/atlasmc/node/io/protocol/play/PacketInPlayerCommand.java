package de.atlasmc.node.io.protocol.play;

import de.atlasmc.IDHolder;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_COMMAND, definition = "player_command")
public class PacketInPlayerCommand extends AbstractPacket implements PacketPlayIn {
	
	public int entityID;
	public Action action;
	public int jumpboost;
	
	public static enum Action implements IDHolder {
		
		START_SNEAKING,
		STOP_SNEAKING,
		LEAVE_BED,
		START_SPRINTING,
		STOP_SPRINTING,
		START_HORSE_JUMP,
		STOP_HORSE_JUMP,
		OPEN_VEHICLE_INVENTORY,
		START_FLYING_ELYTRA;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_COMMAND;
	}

}
