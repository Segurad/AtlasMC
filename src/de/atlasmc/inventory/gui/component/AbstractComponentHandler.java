package de.atlasmc.inventory.gui.component;

import de.atlasmc.inventory.gui.GUI;
import de.atlasmc.inventory.gui.button.Button;
import de.atlasmc.util.Pair;

public abstract class AbstractComponentHandler implements ComponentHandler {

	protected final Component<Object> comp;
	protected final GUI gui;
	protected final int slot;
	protected final int length;
	protected final int depth;
	protected int x;
	protected int y;
	
	protected AbstractComponentHandler(Component<?> comp, GUI gui, int slot, int length, int depth) {
		this(comp, gui, slot, length, depth, 0, 0);
	}
	
	@SuppressWarnings("unchecked")
	protected AbstractComponentHandler(Component<?> comp, GUI gui, int slot, int length, int depth, int offsetX, int offsetY) {
		this.comp = (Component<Object>) comp;
		this.gui = gui;
		this.slot = slot;
		this.length = length;
		this.depth = depth;
		this.x = offsetX;
		this.y = offsetY;
	}

	@Override
	public void updateDisplay() {
		updateDisplay(x, y);
	}
	
	@Override
	public void updateDisplay(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getDisplayX() {
		return x;
	}
	
	@Override
	public int getDisplayY() {
		return y;
	}
	
	@Override
	public void setButtons(Button button) {
		for (int rx = 0; rx<length; rx++) {
			for (int ry = 0; ry<depth; ry++) {
				gui.setButton(slot+rx+(ry*9), button);
			}
		}
	}
	
	@Override
	public void setClickable(boolean value) {
		for (int rx = 0; rx<length; rx++) {
			for (int ry = 0; ry<depth; ry++) {
				gui.setClickable(value, slot+rx+(ry*9));
			}
		}
	}
	
	@Override
	public Pair<Integer, Integer> getCoordsBySlot(int slot) {
		final int rslot = this.slot;
		return new Pair<Integer, Integer>(x+((slot-rslot)%9), y+((slot-rslot)/9));
	}
	
	@Override
	public int getSlotByCoords(int x, int y) {
		x -= this.x;
		y -= this.y;
		if (y >= depth || x >= length) return -1;
		return slot+x+y*9;
	}
	
	@Override
	public Object getSlotEntry(int slot) {
		final int rslot = this.slot;
		return comp.get(x+((slot-rslot)%9), y+((slot-rslot)/9));
	}
	
	@Override
	public void setSlotEntry(int slot, Object entry) {
		setSlotEntry(slot, entry, true);
	}
	
	@Override
	public void setSlotEntry(int slot, Object entry, boolean update) {
		final int rslot = this.slot;
		comp.set(x+((slot-rslot)%9), y+((slot-rslot)/9), entry, update);
	}
	
	@Override
	public Component<?> getComponent() {
		return comp;
	}
	
	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public int getDepth() {
		return depth;
	}

}
