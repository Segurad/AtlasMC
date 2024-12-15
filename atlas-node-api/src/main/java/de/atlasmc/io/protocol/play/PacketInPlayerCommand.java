package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_COMMAND, definition = "player_command")
public class PacketInPlayerCommand extends AbstractPacket implements PacketPlayIn {
	
	public int entityID;
	public Action action;
	public int jumpboost;
	
	public static enum Action implements EnumID, EnumValueCache {
		START_SNEAKING,
		STOP_SNEAKING,
		LEAVE_BED,
		START_SPRINTING,
		STOP_SPRINTING,
		START_HORSE_JUMP,
		STOP_HORSE_JUMP,
		OPEN_VEHICLE_INVENTORY,
		START_FLYING_ELYTRA;
		
		private static List<Action> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Action getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Action> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_COMMAND;
	}

}
