package de.atlasmc.node.event.block;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.block.Block;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class BlockBreakEvent extends BlockEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	private boolean cancelled;
	private final Player player;
	
	public BlockBreakEvent(Block block, Player player) {
		super(block);
		this.player = player;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
