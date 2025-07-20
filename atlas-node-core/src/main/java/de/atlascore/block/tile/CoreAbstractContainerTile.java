package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.AbstractContainerTile;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemPredicate;

public abstract class CoreAbstractContainerTile<I extends Inventory> extends CoreTileEntity implements AbstractContainerTile<I> {
	
	protected I inv;
	protected Chat name;
	protected ItemPredicate lock;
	
	public CoreAbstractContainerTile(BlockType type) {
		super(type);
	}

	@Override
	public I getInventory() {
		if (inv == null) {
			inv = createInventory();
			inv.setTitle(name);
		}
		return inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

	@Override
	public void setCustomName(Chat name) {
		this.name = name; 
		if (inv != null) 
			inv.setTitle(name);
	}

	@Override
	public Chat getCustomName() {
		return name;
	}

	@Override
	public void setLock(ItemPredicate lock) {
		this.lock = lock;
	}

	@Override
	public boolean hasCustomName() {
		return name != null;
	}

	@Override
	public boolean hasLock() {
		return lock != null;
	}

	@Override
	public ItemPredicate getLock() {
		return lock;
	}
	
	protected abstract I createInventory();

}
