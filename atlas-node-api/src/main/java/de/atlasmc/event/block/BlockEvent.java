package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.event.AbstractServerEvent;

public abstract class BlockEvent extends AbstractServerEvent {

	protected Block block;
	
	public BlockEvent(Block block) {
		super(block.getWorld().getServer());
		this.block = block;
	}
	
	public final Block getBlock() {
		return block;
	}
}
