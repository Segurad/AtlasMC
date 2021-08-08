package de.atlasmc.inventory.gui.button;

import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.GUI;

public class OpenGUIButton extends AbstractButton {

	private GUI gui;
	
	public OpenGUIButton(GUI gui, ItemStack icon) {
		super(icon);
		this.gui = gui;
	}
	
	public OpenGUIButton(GUI gui) {
		this(gui, null);
	}
	
	@Override
	public ItemStack press(InventoryClickEvent e) {
		e.getWhoClicked().openInventory(gui);
		return null;
	}

}
