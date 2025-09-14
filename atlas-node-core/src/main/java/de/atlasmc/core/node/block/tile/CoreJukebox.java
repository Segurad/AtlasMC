package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Jukebox;
import de.atlasmc.node.inventory.ItemStack;

public class CoreJukebox extends CoreTileEntity implements Jukebox {
	
	private ItemStack record;
	private long ticksSinceSongStarted;
	
	public CoreJukebox(BlockType type) {
		super(type);
	}

	@Override
	public ItemStack getRecordItem() {
		return record;
	}

	@Override
	public void setRecordItem(ItemStack record) {
		this.record = record;
	}

	@Override
	public long getTicksSinceSongStarted() {
		return ticksSinceSongStarted;
	}

	@Override
	public void setTicksSinceSongStarted(long ticks) {
		this.ticksSinceSongStarted = ticks;
	}

}
