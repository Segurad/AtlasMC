package de.atlasmc.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.entity.HumanEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.component.Component;
import de.atlasmc.inventory.gui.component.ComponentHandler;

public class BaseGUI implements GUI {
	protected final Inventory inv;
	protected final Button[] buttons;
	protected final boolean[] clickable;
	protected final String name;
	protected final List<ComponentHandler> storage = new ArrayList<ComponentHandler>();
	protected final List<GUIListener> listeners = new ArrayList<GUIListener>();

	public BaseGUI(Inventory inv) {
		this(null, inv);
	}

	public BaseGUI(String name, Inventory inv) {
		if (inv == null) throw new IllegalArgumentException("Inventory cannot be null");
		this.inv = inv;
		this.name = name;
		final int size = inv.getSize();
		buttons = new Button[size];
		clickable = new boolean[size];
	}

	@Override
	public void open(Player player) {
		if (player == null) return;
		player.openInventory(inv);
	}

	@Override
	public Button getButton(int slot) {
		return buttons[slot];
	}

	@Override
	public boolean isButtonAt(int slot) {
		return (buttons[slot] == null ? false : true);
	}

	@Override
	public void setButton(int slot, Button button) {
		buttons[slot] = button;
		if (button == null) return;
		final ItemStack icon = button.getIcon(slot);
		if (icon != null) inv.setItem(slot, icon);
	}

	public void update() {
		for (int i = 0; i < buttons.length; i++) {
			Button b = buttons[i];
			if (b == null)
				continue;
			inv.setItem(i, b.getIcon());
		}
	}

	@Override
	public boolean isClickable(int slot) {
		return clickable[slot];
	}

	@Override
	public int getSize() {
		return inv.getSize();
	}

	@Override
	public void clearButtons() {
		Arrays.fill(buttons, null);
	}

	@Override
	public void addComponentHandler(ComponentHandler handler) {
		if (handler == null) return;
		storage.add(handler);
		handler.updateDisplay();
	}
	
	@Override
	public ComponentHandler addComponent(Component<?> component, int slot, int length, int depth) {
		if (component == null) throw new IllegalArgumentException("Cannot create ComponentHandler of null");
		ComponentHandler handler = component.createHandler(this, slot, length, depth);
		storage.add(handler);
		return handler;
	}
	
	@Override
	public void removeComponentHandler(ComponentHandler handler) {
		if (storage.contains(handler)) {
			storage.remove(handler);
		}
	}

	@Override
	public List<ComponentHandler> getComponents() {
		return storage;
	}

	@Override
	public void notifyOpenedBy(Player player) {
		listeners.forEach(l -> l.openedBy(player));
	}

	@Override
	public void addGUIListener(GUIListener listener) {
		listeners.add(0, listener);
	}

	@Override
	public void removeGUIListener(GUIListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void notifyClosedBy(Player player) {
		listeners.forEach(l -> l.closedBy(player));
	}

	@Override
	public void notifyClick(InventoryClickEvent event) {
		listeners.forEach(l -> l.click(event));
	}

	@Override
	public void setClickable(boolean value, int... slot) {
		for (int s : slot) {
			clickable[s] = value;
		}
	}

	@Override
	public ItemStack getItem(int slot) {
		return inv.getItem(slot);
	}

	@Override
	public void setItem(int slot, ItemStack item) {
		if (item != null) {
			if (item.equals(inv.getItem(slot))) return;
			inv.setItem(slot, item);
		} else inv.setItem(slot, null);
	}

	@Override
	public List<GUIListener> getGUIListeners() {
		return listeners;
	}

	@Override
	public void setButton(int slot, Button button, boolean icon) {
		if (icon) setButton(slot, button); else buttons[slot] = button;
	}

	@Override
	public void setButtons(int start, int end, Button button, boolean icon) {	
		for (int i = start; i < end; i++) {
			setButton(i, button, icon);
		}
	}

	@Override
	public List<HumanEntity> getViewers() {
		// TODO Auto-generated method stub
		return null;
	}
}
