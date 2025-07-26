package de.atlasmc.inventory.gui.button;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.ItemStack;

/**
 * Implementation of a Button that switches through the given states each time it is clicked.
 */
public class MultiToggleButton implements Button {

	protected Button[] states;
	protected int state = 0;
	protected String permission;

	protected MultiToggleButton(int states) {
		this.states = new Button[states];
	}
	
	public MultiToggleButton(Button... states) {
		this.states = states;
	}

	public ItemStack press(InventoryClickEvent event) {
		states[state].press(event);
		if ((state + 1) < states.length) {
			state = state + 1;
		} else
			state = 0;
		return getIcon(event.getSlot());
	}

	public ItemStack getIcon() {
		return states[state].getIcon();
	}

	public ItemStack getIcon(int slot) {
		return states[state].getIcon(slot);
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public boolean hasPermission() {
		return permission != null && !permission.equals("");
	}

	public int getState() {
		return state;
	}

	public Button getStateButton() {
		return states[state];
	}

	public void unauthorizedClick(Player player) {
		// override as needed
	}

	@Override
	public boolean hasIcon() {
		return states[state].hasIcon();
	}
	
	@Override
	public MultiToggleButton clone() {
		try {
			MultiToggleButton btn = (MultiToggleButton) super.clone();
			btn.states = new Button[states.length];
			for (int i = 0; i < states.length; i++) {
				btn.states[i] = states[i].clone();
			}
			return btn;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
