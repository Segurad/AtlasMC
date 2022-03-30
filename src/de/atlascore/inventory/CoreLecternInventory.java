package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LecternInventory;

public class CoreLecternInventory extends CoreInventory implements LecternInventory {

	protected static final byte PROPERTY_PAGE_NUMBER = 0;
	
	private int page;
	
	public CoreLecternInventory(Chat title, InventoryHolder holder) {
		super(1, InventoryType.LECTERN, title, holder);
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public void setPage(int page) {
		this.page = page;
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
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}
	
	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_PAGE_NUMBER, page, player);
	}

}
