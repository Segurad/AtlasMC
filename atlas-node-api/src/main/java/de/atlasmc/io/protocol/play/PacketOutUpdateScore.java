package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UPDATE_SCORE)
public class PacketOutUpdateScore extends AbstractPacket implements PacketPlayOut {

	private String entry, objective;
	private ScoreAction action;
	private int value;
	
	public String getEntry() {
		return entry;
	}

	public String getObjective() {
		return objective;
	}

	public ScoreAction getAction() {
		return action;
	}

	public int getValue() {
		return value;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public void setAction(ScoreAction action) {
		this.action = action;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_SCORE;
	}
	
	public static enum ScoreAction {
		UPDATE,
		REMOVE;
		
		private static List<ScoreAction> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static ScoreAction getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<ScoreAction> getValues() {
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
