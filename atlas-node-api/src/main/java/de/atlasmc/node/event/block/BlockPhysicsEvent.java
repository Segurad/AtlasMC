package de.atlasmc.node.event.block;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.block.Block;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.event.ServerHandlerList;

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
	
	public BlockType getChangedType() {
		return changed.getType();
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
