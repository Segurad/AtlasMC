package de.atlasmc.block.data.type;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Waterlogged;

public interface Wall extends Waterlogged {
	
	public Height getHeight(BlockFace face);
	public boolean isUp();
	public void setHeight(BlockFace face, Height height);
	public void setUp(boolean up);
	
	public static enum Height {
		NONE,
		LOW,
		TALL;

		public static Height getByName(String name) {
			name = name.toUpperCase();
			return LOW.name().equals(name) ? LOW : TALL.name().equals(name) ? TALL : NONE;
		}
	}

}
