package de.atlasmc.node.io.protocol.play;

import de.atlasmc.IDHolder;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_GAME_EVENT, definition = "game_event")
public class PacketOutGameEvent extends AbstractPacket implements PacketPlayOut {
	
	public GameEventType event;
	public float value;
	
	@Override
	public int getDefaultID() {
		return OUT_GAME_EVENT;
	}
	
	public static enum GameEventType implements IDHolder {
		
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
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
