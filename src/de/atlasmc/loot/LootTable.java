package de.atlasmc.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.atlasmc.inventory.ItemStack;

public class LootTable {
	
	private final List<Loot> loot;
	private int tickets;
	
	public LootTable() {
		this.loot = new ArrayList<Loot>();
		this.tickets = 1;
	}

	public void addLoot(Loot loot) {
		if (loot == null) return;
		this.loot.add(loot);
		tickets+= loot.getTickets();
	}
	
	public List<ItemStack> getNext(Random random) {
		final int r = random.nextInt(tickets)+1;
		int value = 0;
		for (final Loot l : loot) {
			value += l.getTickets();
			if (value < r) continue;
			return l.getItems(random);
		}
		return new ArrayList<ItemStack>();
	}

}
