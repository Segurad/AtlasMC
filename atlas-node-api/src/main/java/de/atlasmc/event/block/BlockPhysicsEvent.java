package de.atlasmc.event.block;

import de.atlasmc.Material;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class BlockPhysicsEvent extends BlockEvent implements Cancellable {

	private final static ServerHandlerList handlers = new ServerHandlerList();
	private final BlockData changed;
	private final Block sourceBlock;
	private boolean cancelled = false;
	
	public BlockPhysicsEvent(Block block, BlockData changed) {
		this(block, changed, block);
	}
	
	public BlockPhysicsEvent(Block block, BlockData changed, Block sourceBlock) {
		super(block);
		this.changed = changed;
		this.sourceBlock = sourceBlock;
	}
	
	public Block getSourceBlock() {
		return sourceBlock;
	}
	
	public Material getChangedType() {
		return changed.getMaterial();
	}

	public BlockData getChanged() {
		return changed;
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
