package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class BlockBreakEvent extends BlockEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	private boolean cancelled;
	
	public BlockBreakEvent(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
