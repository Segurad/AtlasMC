package de.atlasmc.block.tile;

public interface CommandBlock extends TileEntity {
	
	public Mode getMode();
	
	public void setMode(Mode mode);
	
	public boolean isConditional();
	
	public void setConditional(boolean value);
	
	public boolean isAlwaysActive();
	
	public void setAlwaysActive(boolean value);
	
	public boolean getTrackOutput();
	
	public void setTrackOutput(boolean value);
	
	public static enum Mode {
		SEQUENCE,
		AUTO,
		REDSTONE;

		public static Mode getByID(int mode) {
			return values()[mode];
		}
	}

}
