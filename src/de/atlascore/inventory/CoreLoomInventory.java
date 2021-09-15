package de.atlascore.inventory;

import de.atlasmc.block.tile.Banner.Pattern;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LoomInventory;

public class CoreLoomInventory extends CoreInventory implements LoomInventory {

	protected static final byte
	PROPERTY_PATTERN = 0;
	
	private Pattern pattern;
	
	public CoreLoomInventory(String title, InventoryHolder holder) {
		super(4, InventoryType.LOOM, title, holder);
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		switch (slot) {
		case 0:
		case 1:
		case 2:
			return SlotType.CRAFTING;
		case 3:
			return SlotType.RESULT;
		default:
			throw new IllegalArgumentException("Invalid slot index: " + slot);
		}
	}

	@Override
	public ItemStack getBanner() {
		return getItem(0);
	}

	@Override
	public ItemStack getDye() {
		return getItem(1);
	}

	@Override
	public ItemStack getPatternItem() {
		return getItem(2);
	}

	@Override
	public void setBanner(ItemStack banner) {
		setItem(0, banner);
	}

	@Override
	public void setDye(ItemStack dye) {
		setItem(1, dye);
	}

	@Override
	public void setPatternItem(ItemStack pattern) {
		setItem(2, pattern);
	}

	@Override
	public ItemStack getResult() {
		return getItem(3);
	}

	@Override
	public void setResult(ItemStack result) {
		setItem(3, result);
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
		updateProperty(PROPERTY_PATTERN, pattern.getLoomID());
	}
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}
	
	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_PATTERN, pattern.getLoomID(), player);
	}

}
