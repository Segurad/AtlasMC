package de.atlasmc.inventory.gui;

import de.atlasmc.Material;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.button.AbstractButton;
import de.atlasmc.inventory.gui.component.ComponentHandler;
import de.atlasmc.inventory.gui.component.ItemComponentHandler;
import de.atlasmc.inventory.gui.component.ItemPageComponent;
import de.atlasmc.inventory.gui.component.PageComponent;

public class MultipageGUI extends BaseGUI {

	private final int next;
	private final int back;
	private int page = 0;
	protected final ItemPageComponent mltPageItems;
	protected final ItemComponentHandler mhandler;
	private ItemStack inext = null;
	private ItemStack iback = null;
	private final ItemStack air = new ItemStack(Material.AIR);
	
	public MultipageGUI(Inventory inv, int back, int next, int compLenght, int compDepth, int maxpages) {
		this(inv, back, next, compLenght, compDepth, maxpages, 0, 0);
	}
	
	/**
	 * Creates a new MultipageGUI. The given Inventory must be of {@link InventoryType#GENERIC_9X6} or {@link InventoryType#DOUBLE_CHEST}
	 * @param inv
	 * @param back
	 * @param next
	 * @param compLenght
	 * @param compDepth
	 * @param maxpages
	 * @param compOffsetX
	 * @param compOffsetY
	 */
	public MultipageGUI(Inventory inv, int back, int next, int compLenght, int compDepth, int maxpages, int compOffsetX, int compOffsetY) {
		super(inv);
		if (inv.getType() != InventoryType.GENERIC_9X6 || inv.getType() != InventoryType.DOUBLE_CHEST) {
			throw new IllegalArgumentException("Invalid inventory type: " + inv.getType().name());
		}
		this.next = next;
		this.back = back;
		this.mltPageItems = new ItemPageComponent(compLenght, compDepth, maxpages);
		this.mhandler = mltPageItems.createHandler(this, compOffsetY*9+compOffsetX, compLenght, compDepth, false);
		addComponentHandler(mhandler);
		AbstractButton btnPage = new AbstractButton() {
			@Override
			public ItemStack press(InventoryClickEvent e) {
				final int slot = e.getSlot();
				if (slot == back) {
					if (page > 0) {
						page--;
						mhandler.updateDisplay(0, mhandler.getDepth()*page);
						if (page+2 == mltPageItems.getPages()) 
							inv.setItem(next, inext);
						if (page == 0) 
							return air;
					}
				} else if (slot == next) {
					if (page+1 < mltPageItems.getPages()) {
						page++;
						mhandler.updateDisplay(0, mhandler.getDepth()*page);
						if (page-1 == 0) 
							inv.setItem(back, iback);
						if (page+1 == mltPageItems.getPages()) 
							return air;
					}
				}
				return null;
			}
			
			@Override
			public ItemStack getIcon(int slot) {
				if (slot == back && page > 0) {
					return iback;
				} else if (slot == next && page+1 < mltPageItems.getPages()) {
					return inext;
				}
				return null;
			}
		};
		
		setButton(next, btnPage);
		setButton(back, btnPage);
	}
	
	public void setPageSwitchIcons(ItemStack next, ItemStack back) {
		this.iback = back;
		this.inext = next;
		if (page+1 == mltPageItems.getPages()) 
			inv.setItem(this.next, null); 
		else 
			inv.setItem(this.next, inext);
		if (page-1 == 0) 
			inv.setItem(this.back, iback); 
		else 
			inv.setItem(this.back, null);
	}
	
	public int getCurrentPage() {
		return page;
	}
	
	public int getPages() {
		return mltPageItems.getPages();
	}
	
	public int getMaxPages() {
		return mltPageItems.getMaxPages();
	}
	
	public int addPage() {
		int index = mltPageItems.addPage();
		if (index != -1) {
			inv.setItem(next, inext);
		}
		return index;
	}
	
	public PageComponent<ItemStack> getPageComponent() {
		return mltPageItems;
	}
	
	public ComponentHandler getPageHandler() {
		return mhandler;
	}
}
