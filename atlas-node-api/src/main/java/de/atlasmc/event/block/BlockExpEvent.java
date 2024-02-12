package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.event.ServerHandlerList;

public class BlockExpEvent extends BlockEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	private int exp;
	
	public BlockExpEvent(Block block, int exp) {
		super(block);
		this.exp = exp;
	}
	
	public int getExpToDrop() {
		return exp;
	}
	
	public void setExpToDrop(int exp) {
		this.exp = exp;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
