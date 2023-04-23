package de.atlasmc.block.tile;

import java.util.List;

import de.atlasmc.Nameable;
import de.atlasmc.chat.Chat;
import de.atlasmc.command.CommandSender;

public interface CommandBlock extends TileEntity, Nameable, CommandSender {
	
	public Mode getMode();
	
	public void setMode(Mode mode);
	
	public boolean isConditional();
	
	public void setConditional(boolean conditional);
	
	public boolean isAlwaysActive();
	
	public void setAlwaysActive(boolean alwaysActive);
	
	public boolean getTrackOutput();
	
	public void setTrackOutput(boolean trackOutput);
	
	public void setCommand(String command);
	
	public String getCommand();
	
	public Chat getLastMessage();
	
	/**
	 * While {@link #sendMessage(Chat)} does only set the last message if {@link #getTrackOutput()} is true,
	 * this method will always set the last output
	 * @param message
	 */
	public void setLastMessage(Chat message);
	
	public void setPowered(boolean powered);
	
	public boolean isPowered();
	
	public void setSuccessCount(int count);
	
	public int getSuccessCount();
	
	public static enum Mode {
		SEQUENCE,
		AUTO,
		REDSTONE;

		private static List<Mode> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Mode getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Mode> getValues() {
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
