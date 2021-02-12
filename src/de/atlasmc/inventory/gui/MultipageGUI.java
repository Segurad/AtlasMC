package de.atlasmc.inventory.gui;

import de.atlascore.inventory.gui.CoreGUI;
import de.atlasmc.Material;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.component.ComponentHandler;
import de.atlasmc.inventory.gui.component.ItemComponentHandler;
import de.atlasmc.inventory.gui.component.ItemPageComponent;
import de.atlasmc.inventory.gui.component.PageComponent;

public class MultipageGUI extends CoreGUI {

	private final int next, back;
	private int page = 0;
	protected final ItemPageComponent mltPageItems;
	protected final ItemComponentHandler mhandler;
	private ItemStack inext = null, iback = null;
	private final ItemStack air = new ItemStack(Material.AIR);
	
	public MultipageGUI(String name, int back, int next, int compLenght, int compDepth, int maxpages) {
		this(name, back, next, compLenght, compDepth, maxpages, 0, 0);
	}
	
	public MultipageGUI(String name, int back, int next, int compLenght, int compDepth, int maxpages, int compOffsetX, int compOffsetY) {
		super(9*6, name);
		this.next = next;
		this.back = back;
		this.mltPageItems = new ItemPageComponent(compLenght, compDepth, maxpages);
		this.mhandler = mltPageItems.createHandler(this, compOffsetY*9+compOffsetX, compLenght, compDepth, false);
		addComponentHandler(mhandler);
		ActionButton btnPage = new ActionButton() {
			@Override
			public ItemStack press(InventoryClickEvent e) {
				final int slot = e.getSlot();
				if (slot == back) {
					if (page > 0) {
						page--;
						mhandler.updateDisplay(0, mltPageItems.getLengthY()*page);
						if (page+2 == mltPageItems.getPages()) setItem(next, inext);
						if (page == 0) return air;
					}
				} else if (slot == next) {
					if (page+1 < mltPageItems.getPages()) {
						page++;
						mhandler.updateDisplay(0, mltPageItems.getLengthY()*page);
						if (page-1 == 0) setItem(back, iback);
						if (page+1 == mltPageItems.getPages()) return air;
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
		if (page+1 == mltPageItems.getPages()) setItem(this.next, null); else setItem(this.next, inext);
		if (page-1 == 0) setItem(this.back, iback); else setItem(this.back, null);
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
			setItem(next, inext);
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
