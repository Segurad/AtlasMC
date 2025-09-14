package de.atlasmc.core.node.block.tile;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.AbstractContainerTile;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.ItemPredicate;

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
