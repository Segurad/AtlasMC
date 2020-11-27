package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.event.HandlerList;

public class BlockExpEvent extends BlockEvent {

	private static final HandlerList handlers = new HandlerList();
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
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
