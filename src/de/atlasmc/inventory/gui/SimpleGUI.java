package de.atlasmc.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlascore.inventory.CoreInventory;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.component.Component;
import de.atlasmc.inventory.gui.component.ComponentHandler;

public class SimpleGUI extends CoreInventory implements GUI {

	protected Button[] buttons;
	protected boolean[] clickable;
	protected final String name;
	protected List<ComponentHandler> components;
	protected List<GUIListener> listeners;
	
	public SimpleGUI(int size, String name) {
		super(size);
		this.name = name;
	}

	@Override
	public Button getButton(int slot) {
		return buttons == null ? null : buttons[slot];
	}

	@Override
	public boolean isButtonAt(int slot) {
		return buttons == null ? false : (buttons[slot] == null ? false : true);
	}

	@Override
	public void setButton(int slot, Button button) {
		if (buttons == null) buttons = new Button[getSize()]; 
		buttons[slot] = button;
		if (button == null) return;
		final ItemStack icon = button.getIcon(slot);
		if (icon != null) setItem(slot, icon);
	}

	public void update() {
		for (int i = 0; i < buttons.length; i++) {
			Button b = buttons[i];
			if (b == null)
				continue;
			setItem(i, b.getIcon());
		}
	}

	@Override
	public boolean isClickable(int slot) {
		return clickable == null ? false : clickable[slot];
	}

	@Override
	public void clearButtons() {
		if (buttons != null)
		Arrays.fill(buttons, null);
	}

	@Override
	public void addComponentHandler(ComponentHandler handler) {
		if (handler == null) return;
		if (components == null) components = new ArrayList<ComponentHandler>();
		components.add(handler);
		handler.updateDisplay();
	}
	
	@Override
	public ComponentHandler addComponent(Component<?> component, int slot, int length, int depth) {
		if (component == null) throw new IllegalArgumentException("Cannot create ComponentHandler of null");
		ComponentHandler handler = component.createHandler(this, slot, length, depth);
		addComponentHandler(handler);
		return handler;
	}
	
	@Override
	public void removeComponentHandler(ComponentHandler handler) {
		if (components != null && components.contains(handler)) {
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
		if (listeners == null) listeners = new ArrayList<GUIListener>();
		listeners.add(0, listener);
	}

	@Override
	public void removeGUIListener(GUIListener listener) {
		if (listeners != null)
		listeners.remove(listener);
	}

	@Override
	public void notifyClosedBy(Player player) {
		if (listeners != null)
		listeners.forEach(l -> l.closedBy(player));
	}

	@Override
	public void notifyClick(InventoryClickEvent event) {
		if (listeners != null)
		listeners.forEach(l -> l.click(event));
	}

	@Override
	public void setClickable(boolean value, int... slot) {
		if (clickable == null) clickable = new boolean[getSize()];
		for (int s : slot) {
			clickable[s] = value;
		}
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
	public void setButtons(int offset, int length, Button button, boolean icon) {	
		for (int i = offset; i < length; i++) {
			setButton(i, button, icon);
		}
	}
}
