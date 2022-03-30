package de.atlasmc.block.tile;

import de.atlasmc.Nameable;
import de.atlasmc.chat.Chat;

public interface CommandBlock extends TileEntity, Nameable {
	
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
	
	public void setLastOutput(Chat lastoutput);
	
	public Chat getLastOutput();
	
	public void setPowered(boolean powered);
	
	public boolean isPowered();
	
	public void setSuccessCount(int count);
	
	public int getSuccessCount();
	
	public static enum Mode {
		SEQUENCE,
		AUTO,
		REDSTONE;

		public static Mode getByID(int mode) {
			return values()[mode];
		}
	}

}
