package de.atlasmc.chat.component;

import de.atlasmc.inventory.ItemStack;

public class HoverItemEvent implements HoverEvent {
	
	private ItemStack item;
	
	public HoverItemEvent(ItemStack item) {
		this.item = item;
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ITEM;
	}

	@Override
	public String getValue() {
		return null; // TODO item to nbt json
	}

}
