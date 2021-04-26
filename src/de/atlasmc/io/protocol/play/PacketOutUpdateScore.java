package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutUpdateScore extends Packet {

	public String getName();
	public String getObjectiveName();
	public ScoreAction getAction();
	public int getValue();
	
	@Override
	public default int getDefaultID() {
		return 0x4D;
	}
	
	public static enum ScoreAction {
		UPDATE,
		REMOVE;
		
		public static ScoreAction getByID(int id) {
			return values()[id];
		}
	}
}
