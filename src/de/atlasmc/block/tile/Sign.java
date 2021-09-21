package de.atlasmc.block.tile;

import de.atlasmc.chat.component.ChatComponent;

public interface Sign extends TileEntity {
	
	public ChatComponent[] getLines();
	
	public void setLines(ChatComponent[] lines);
	
	public void setLine(int index, ChatComponent line);
	
	public ChatComponent getLine(int index);

}
