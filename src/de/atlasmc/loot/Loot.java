package de.atlasmc.loot;

import java.util.List;
import java.util.Random;

import de.atlasmc.inventory.ItemStack;

public interface Loot {

	public int getTickets();
	public List<ItemStack> getItems(final Random random);
}
