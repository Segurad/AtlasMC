package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CHANGE_GAME_STATE)
public class PacketOutChangeGameState extends AbstractPacket implements PacketPlayOut {
	
	private ChangeReason reason;
	private float value;
	
	public ChangeReason getReason() {
		return reason;
	}
	
	public void setReason(ChangeReason reason) {
		this.reason = reason;
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_CHANGE_GAME_STATE;
	}
	
	public static enum ChangeReason {
		NO_RESPAWN_REASON,
		END_RAINING,
		BEGIN_RAINING,
		CHANGE_GAMEMODE,
		WIN_GAME,
		DEMO_EVENT,
		ARROW_HIT_PLAYER,
		RAIN_LEVEL_CHANGE,
		THUNDER_LEVEL_CHANGE,
		PLAY_PUFFERFISH_STING_SOUND,
		PLAY_ELDER_GUARDIAN_MOB_APEAREANCE,
		ENABLE_RESPAWN_SCREEN;
		
		private static List<ChangeReason> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static ChangeReason getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<ChangeReason> getValues() {
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
