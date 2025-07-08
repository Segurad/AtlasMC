package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Jukebox;
import de.atlasmc.inventory.ItemStack;

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
