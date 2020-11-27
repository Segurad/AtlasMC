package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.event.Event;

public abstract class BlockEvent extends Event {

	protected Block block;
	
	public BlockEvent(Block block) {
		this.block = block;
	}
	
	public final Block getBlock() {
		return block;
	}
}
