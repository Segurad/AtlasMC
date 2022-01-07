package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UPDATE_SCORE)
public interface PacketOutUpdateScore extends PacketPlay, PacketOutbound {

	public String getEntry();
	
	public String getObjectiveName();
	
	public ScoreAction getAction();
	
	public int getValue();
	
	public void setEntry(String name);
	
	public void setObjective(String name);
	
	public void setAction(ScoreAction action);
	
	public void setValue(int value);
	
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
