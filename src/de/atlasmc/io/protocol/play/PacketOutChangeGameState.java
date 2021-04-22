package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutChangeGameState extends Packet {
	
	public ChangeReason getReason();
	public float getValue();
	
	@Override
	default int getDefaultID() {
		return 0x1D;
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
		
		public static ChangeReason getByID(int id) {
			return values()[id];
		}
	}

}
