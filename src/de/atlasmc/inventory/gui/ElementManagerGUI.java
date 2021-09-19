package de.atlasmc.inventory.gui;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.event.inventory.ClickType;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemUtil;
import de.atlasmc.inventory.gui.component.AbstractPageComponent;
import de.atlasmc.inventory.gui.component.ComponentHandler;
import de.atlasmc.inventory.gui.button.AbstractButton;
import de.atlasmc.inventory.gui.component.AbstractComponentHandler;
import de.atlasmc.inventory.gui.component.PageComponent;

/**
 * a 9x6 GUI with a 9x4 array of elements
 * @author Segurad
 *
 */
public abstract class ElementManagerGUI<E> extends MultipageGUI {

	protected final PageComponent<E> elements;
	protected final ComponentHandler ehandler;
	
	public ElementManagerGUI(String name, int add, ItemStack addicon) {
		this(name, add, addicon, 0);
	}

	/**
	 * 
	 * @param name The GUI's name
	 * @param clazz
	 * @param add value 0-8
	 * @param addicon
	 * @param maxpages
	 */
	@SuppressWarnings("unchecked")
	public ElementManagerGUI(String name, int add, ItemStack addicon, int maxpages) {
		super(name, 45, 46, 9, 4, maxpages);
		if (add > 8 || add < 0) throw new IllegalArgumentException("Value must be between 0 and 8");
		elements = new AbstractPageComponent<E>(9, 4, maxpages) {
			@Override
			public ComponentHandler createHandler(GUI gui, int slot, int length, int depth, int offsetX, int offsetY) {
				return new AbstractComponentHandler(elements, gui, slot, length, depth) {
					@Override
					public void internalUpdate(int x, int y) {}		
				};
			}
			
		};
		ehandler = elements.createHandler(this, 0, 9, 4);
		setButtons(0, 36, new AbstractButton() {
			@Override
			public ItemStack press(InventoryClickEvent e) {
				final int slot = e.getSlot();
				final E el = (E) ehandler.getSlotEntry(getCurrentPage()*9*4+slot);
				if (el == null) return null;
				final ClickType click = e.getClick();
				if (click == ClickType.LEFT) {
					editElement(el);
				} else if (click == ClickType.RIGHT) {
					if (!removeElement(el)) return null;
					removeElement(slot);
				}
				return null;
			}
		}, false);
		setButton(45+add, new AbstractButton(addicon) {
			@Override
			public ItemStack press(InventoryClickEvent e) {
				if (e.getClick() != ClickType.LEFT) return null;
				final E el = createElement();
				addElement(el);
				return null;
			}
		});
		setPageSwitchIcons(ItemUtil.getItemStack(Material.ARROW, ChatUtil.toChat("§7-->")), ItemUtil.getItemStack(Material.ARROW, ChatUtil.toChat("§7<--")));
	}
	
	private final void removeElement(int slot) {
		final int slotbase = getCurrentPage()*9*4+slot;
		ehandler.setSlotEntry(slotbase, null);
		mhandler.setSlotEntry(slotbase, null);
		for (int i = slotbase; i < 36; i++) {
			if (ehandler.getSlotEntry(i+1) == null) {
				return;
			}
			ehandler.setSlotEntry(i,ehandler.getSlotEntry(i+1));
			ehandler.setSlotEntry(i+1, null);
			mhandler.setSlotEntry(i,mhandler.getSlotEntry(i+1));
			mhandler.setSlotEntry(i+1, null);
		}
	}
	
	protected abstract E createElement();
	/**
	 * 
	 * @param element
	 * @return a new icon for the Element
	 */
	protected abstract ItemStack createIcon(E element);
	/**
	 * notification by the ElementGUI open an other editor for the element 
	 * @param element
	 */
	protected abstract void editElement(E element);
	/**
	 * 
	 * @param element
	 * @return true if the element can be removed
	 */
	protected abstract boolean removeElement(E element);
	
	public final void updateElementIcon(E element, ItemStack item) {
		int page = -1, y = -1, x = -1;
		final List<E[][]> valueList = getElements().getEntrieList();
		for (final E[][] values : valueList) {
			page++;
			for (final E[] values0 : values) {
				y++;
				for (final E value : values0) {
					x++;
					if (value != element) continue;
					mhandler.setSlotEntry(page*9+y*9+x, item);
				}
			}
		}
	}
	
	public final boolean addElement(E element) {
		if (!elements.add(element, false)) {
			int  index  = elements.addPage();
			if (index == -1) return false;
			elements.set(0, index * 4, element, false);
			addPage();
			mltPageItems.set(0, index * 4, createIcon(element));
		} else mltPageItems.add(createIcon(element));
		return true;
	}
	
	public PageComponent<E> getElements() {
		return elements;
	}
}
