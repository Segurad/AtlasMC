package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

@DefaultPacketID(packetID = PacketPlay.OUT_GAME_EVENT, definition = "game_event")
public class PacketOutGameEvent extends AbstractPacket implements PacketPlayOut {
	
	public GameEventType event;
	public float value;
	
	@Override
	public int getDefaultID() {
		return OUT_GAME_EVENT;
	}
	
	public static enum GameEventType implements EnumID, EnumValueCache {
		
		NO_RESPAWN_BLOCK_AVAILABLE,
		BEGIN_RAINING,
		END_RAINING,
		CHANGE_GAMEMODE,
		WIN_GAME,
		DEMO_EVENT,
		ARROW_HIT_PLAYER,
		RAIN_LEVEL_CHANGE,
		THUNDER_LEVEL_CHANGE,
		PLAY_PUFFERFISH_STING_SOUND,
		PLAY_ELDER_GUARDIAN_MOB_APEARANCE,
		ENABLE_RESPAWN_SCREEN,
		LIMITED_CRAFTING,
		START_WAITING_FOR_LEVEL_CHUNKS;
		
		private static List<GameEventType> VALUES;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static GameEventType getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<GameEventType> getValues() {
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

}
