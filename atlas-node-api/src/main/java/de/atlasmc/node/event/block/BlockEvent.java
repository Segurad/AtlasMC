package de.atlasmc.node.event.block;

import de.atlasmc.node.block.Block;
import de.atlasmc.node.event.AbstractServerEvent;

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
