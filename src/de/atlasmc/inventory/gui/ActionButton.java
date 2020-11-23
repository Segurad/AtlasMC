package de.atlasmc.inventory.gui;

import de.atlasmc.entity.Player;
import de.atlasmc.inventory.ItemStack;

public abstract class ActionButton implements Button {

	protected ItemStack icon;
	protected String permission;

	public ActionButton(ItemStack icon) {
		this.icon = icon;
	}

	public ActionButton() {
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
		return icon == null ? false : true;
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
		return permission == null ? false : !permission.equals("");
	}

	@Override
	public void unauthorizedClick(Player player) {}
	
	@Override
	public ActionButton clone() {
		try {
			ActionButton btn = (ActionButton) super.clone();
			if (icon != null) btn.icon = icon.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
