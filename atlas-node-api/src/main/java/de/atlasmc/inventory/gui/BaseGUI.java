package de.atlasmc.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.button.Button;
import de.atlasmc.inventory.gui.component.Component;
import de.atlasmc.inventory.gui.component.ComponentHandler;

public class BaseGUI implements GUI {

	protected Button[] buttons;
	protected boolean[] clickable;
	protected List<ComponentHandler> components;
	protected List<GUIListener> listeners;
	protected Inventory inv;
	
	public BaseGUI(Inventory inv) {
		if (inv == null)
			throw new IllegalAccessError("Inventory can not be null!");
		this.inv = inv;
	}

	@Override
	public Button getButton(int slot) {
		return buttons == null ? null : buttons[slot];
	}

	@Override
	public boolean isButtonAt(int slot) {
		return buttons != null && (buttons[slot] != null);
	}

	@Override
	public void setButton(int slot, Button button) {
		setButton(slot, button, true);
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
		return clickable != null && clickable[slot];
	}

	@Override
	public void clearButtons() {
		if (buttons != null)
			Arrays.fill(buttons, null);
	}

	@Override
	public void addComponentHandler(ComponentHandler handler) {
		if (handler == null) 
			throw new IllegalArgumentException("Handler can not be null!");
		if (components == null) 
			components = new ArrayList<>();
		components.add(handler);
		handler.updateDisplay();
	}
	
	@Override
	public ComponentHandler addComponent(Component<?> component, int slot, int length, int depth) {
		if (component == null) 
			throw new IllegalArgumentException("Component can not be null!");
		ComponentHandler handler = component.createHandler(this, slot, length, depth);
		addComponentHandler(handler);
		return handler;
	}
	
	@Override
	public void removeComponentHandler(ComponentHandler handler) {
		if (handler == null)
			return;
		if (components != null) {
			components.remove(handler);
		}
	}

	@Override
	public List<ComponentHandler> getComponents() {
		return components;
	}

	@Override
	public void notifyOpenedBy(Player player) {
		if (listeners != null)
			listeners.forEach(l -> l.openedBy(player));
	}

	@Override
	public void addGUIListener(GUIListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		if (listeners == null) 
			listeners = new ArrayList<>();
		listeners.add(0, listener);
	}

	@Override
	public void removeGUIListener(GUIListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	@Override
	public void notifyClosedBy(Player player) {
		if (listeners == null || listeners.isEmpty())
			return;
		for (GUIListener l : listeners)
			l.closedBy(player);
	}

	@Override
	public void notifyClick(InventoryClickEvent event) {
		if (listeners == null || listeners.isEmpty())
			return;
		for (GUIListener l : listeners)
			l.click(event);
	}

	@Override
	public void setClickable(boolean value, int... slots) {
		if (slots == null)
			throw new IllegalArgumentException("Slots can not be null!");
		if (clickable == null) 
			clickable = new boolean[inv.getSize()];
		for (int s : slots) {
			clickable[s] = value;
		}
	}
	
	@Override
	public void setClickable(boolean value) {
		Arrays.fill(clickable, value);
	}

	@Override
	public List<GUIListener> getGUIListeners() {
		return listeners;
	}

	@Override
	public void setButton(int slot, Button button, boolean icon) {
		if (button == null)
			throw new IllegalArgumentException("Button can not be null!");
		if (buttons == null) 
			buttons = new Button[inv.getSize()]; 
		buttons[slot] = button;
		if (!icon)
			return;
		final ItemStack itemIcon = button.getIcon(slot);
		if (itemIcon != null) 
			inv.setItem(slot, itemIcon);
	}

	@Override
	public void setButtons(int offset, int length, Button button, boolean icon) {	
		for (int i = offset; i < length; i++) {
			setButton(i, button, icon);
		}
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}

	@Override
	public void notifyRemoved() {
		if (listeners == null || listeners.isEmpty())
			return;
		for (GUIListener l : listeners)
			l.removed();
	}
	
}
