package de.atlasmc.event.block;

import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.block.Block;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ServerHandlerList;

public abstract class BlockEvent extends GenericEvent<LocalServer, ServerHandlerList> {

	protected Block block;
	
	public BlockEvent(Block block) {
		super(block.getWorld().getServer());
		this.block = block;
	}
	
	public final Block getBlock() {
		return block;
	}
}
