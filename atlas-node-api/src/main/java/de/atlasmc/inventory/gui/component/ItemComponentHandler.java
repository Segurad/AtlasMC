package de.atlasmc.inventory.gui.component;

import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.GUI;

public final class ItemComponentHandler extends AbstractComponentHandler {

	protected final Component<ItemStack> comp;
	private final Inventory inv;
	
	/**
	 * 
	 * @param gui the applied GUI
	 * @param component the used StorrageContainer
	 * @param slot the display's left upper corner
	 * @param length the display length
	 * @param depth the display depth
	 */
	ItemComponentHandler(Component<ItemStack> comp, GUI gui, int slot, int length, int depth, int offsetX, int offsetY, boolean slotwatcher) {
		super(comp, gui, slot, length, depth, offsetX, offsetY);
		if (length > comp.getLengthX()) 
			throw new IllegalArgumentException("The display length can not be heigher than the component length!");
		if (depth > comp.getLengthY()) 
			throw new IllegalArgumentException("The display depth can not be heigher than the component depth!");
		this.comp = comp;
		this.inv = null;
		if (slotwatcher) {
			/*Button b = new RealClickButton() {
				@Override
				protected boolean notifyShifted(ItemStack item, Inventory sourceinv, int sourceslot, boolean cancelled, int hotbar) {
					return cancelled;
				}
				
				@Override
				protected boolean notifyDroped(ItemStack item, ItemStack slotitem, Inventory sourceinv, int sourceslot, boolean cancelled) {
					return cancelled;
				}
				
				@Override
				protected boolean notifyDoubleClick(Inventory inv, int slot, boolean cancelled) {
					return cancelled;
				}
				
				@Override
				protected boolean notifyCursor(ItemStack citem, ItemStack oldcitem, ItemStack item, ItemStack olditem, Inventory sourceinv, int sourceslot, boolean cancelled) {
					return cancelled;
				}
				
				@Override
				protected boolean notifyClickedSlot(ItemStack item, ItemStack olditem, Inventory inv, int slot, boolean cancelled, int state) {
					if (cancelled) return cancelled;
					final Pair<Integer, Integer> coords = getCoordsBySlot(slot);
					comp.set(coords.getValue1(), coords.getValue2(), item, ItemComponentHandler.this);
					return cancelled;
				}
			};*/
			//setButtons(b);
		}
	}
	
	@Override
	public void updateDisplay() {
		updateDisplay(x, y);
	}
	
	@Override
	public void updateDisplay(int x, int y) {
		super.updateDisplay(x, y);
		for (int rx = 0; rx<length; rx++) {
			for (int ry = 0; ry<depth; ry++) {
				ItemStack item = comp.get(x+rx, y+ry);
				inv.setItem(slot+rx+(ry*9), item);
			}
		}
	}
	
	@Override
	public void internalUpdate(final int x, final int y) {
		int slot = getSlotByCoords(x, y);
		if (slot == -1) 
			return;
		inv.setItem(slot, comp.get(x, y));
	}

	@Override
	public Component<ItemStack> getComponent() {
		return comp;
	}

}
