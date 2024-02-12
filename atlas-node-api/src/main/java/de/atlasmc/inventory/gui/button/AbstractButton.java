package de.atlasmc.inventory.gui.button;

import de.atlasmc.entity.Player;
import de.atlasmc.inventory.ItemStack;

/**
 * Abstract implementation of a Button
 */
public abstract class AbstractButton implements Button {

	protected ItemStack icon;
	protected String permission;

	public AbstractButton(ItemStack icon) {
		this.icon = icon;
	}

	public AbstractButton() {
		icon = null;
	}

	@Override
	public ItemStack getIcon() {
		return icon;
	}

	@Override
	public ItemStack getIcon(int slot) {
		return icon;
	}
	
	@Override
	public boolean hasIcon() {
		return icon != null;
	}

	@Override
	public String getPermission() {
		return permission;
	}

	@Override
	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public boolean hasPermission() {
		return permission != null && !permission.equals("");
	}

	@Override
	public void unauthorizedClick(Player player) {}
	
	@Override
	public AbstractButton clone() {
		try {
			AbstractButton btn = (AbstractButton) super.clone();
			if (icon != null) 
				btn.icon = icon.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}
}
