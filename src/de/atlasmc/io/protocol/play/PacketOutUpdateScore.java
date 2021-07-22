package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UPDATE_SCORE)
public interface PacketOutUpdateScore extends PacketPlay, PacketOutbound {

	public String getName();
	public String getObjectiveName();
	public ScoreAction getAction();
	public int getValue();
	
	@Override
	public default int getDefaultID() {
		return OUT_UPDATE_SCORE;
	}
	
	public static enum ScoreAction {
		UPDATE,
		REMOVE;
		
		public static ScoreAction getByID(int id) {
			return values()[id];
		}
	}
}
