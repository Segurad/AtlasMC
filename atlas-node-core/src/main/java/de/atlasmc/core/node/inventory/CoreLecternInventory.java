package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.LecternInventory;

public class CoreLecternInventory extends CoreInventory implements LecternInventory {

	protected static final byte PROPERTY_PAGE_NUMBER = 0;
	
	public CoreLecternInventory(Chat title, InventoryHolder holder) {
		super(1, InventoryType.LECTERN, title, holder);
	}
	
	@Override
	protected int getPropertyCount() {
		return 1;
	}

	@Override
	public int getPage() {
		return properties[PROPERTY_PAGE_NUMBER];
	}

	@Override
	public void setPage(int page) {
		updateProperty(PROPERTY_PAGE_NUMBER, page);
	}

	@Override
	public ItemStack getBook() {
		return getItem(0);
	}

	@Override
	public void setBook(ItemStack book) {
		setItem(0, book);
	}

}
