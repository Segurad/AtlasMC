package de.atlasmc.block.tile;

import de.atlasmc.chat.Chat;

public interface Sign extends TileEntity {
	
	public Chat[] getLines();
	
	public void setLines(Chat[] lines);
	
	public void setLine(int index, Chat line);
	
	public Chat getLine(int index);

}
