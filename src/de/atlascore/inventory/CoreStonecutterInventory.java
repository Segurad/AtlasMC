package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.StonecutterInventory;

public class CoreStonecutterInventory extends CoreInventory implements StonecutterInventory {

	protected static final byte
	PROPERTY_SELECTED_RECIPE = 0;
	
	private short selectedRecipe = -1;
	
	public CoreStonecutterInventory(Chat title, InventoryHolder holder) {
		super(2, InventoryType.STONECUTTER, title, holder);
	}

	@Override
	public ItemStack getResult() {
		return getItem(1);
	}

	@Override
	public void setResult(ItemStack result) {
		setItem(1, result);
	}

	@Override
	public ItemStack getInput() {
		return getItem(0);
	}

	@Override
	public void setInput(ItemStack input) {
		setItem(0, input);
	}

	@Override
	public int getSelectedRecipe() {
		return selectedRecipe;
	}

	@Override
	public void setSelectedRecipe(int value) {
		selectedRecipe = (short) value;
		updateProperty(PROPERTY_SELECTED_RECIPE, value);
	}
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}
	
	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_SELECTED_RECIPE, selectedRecipe, player);
	}

}
