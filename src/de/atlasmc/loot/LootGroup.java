package de.atlasmc.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.atlasmc.inventory.ItemStack;

public final class LootGroup implements Loot {

	private final LootItem[] items;
	private final int[] chances;
	private final int tickets, maxamount;
	
	public LootGroup(LootItem[] items, int[] chances, int maxamount) {
		this(items, chances, 0, maxamount);
	}
	
	public LootGroup(LootItem[] items, int[] chances, int maxamount, int tickets) {
		this.items = items;
		this.chances = chances;
		this.tickets = tickets;
		this.maxamount = maxamount;
	}
	
	public LootGroup(LootItem item, int tickets) {
		this.items = new LootItem[] {
				item
		};
		this.chances = new int[] {
				1000
		};
		this.tickets = tickets;
		this.maxamount = -1;
	}

	@Override
	public int getTickets() {
		return tickets;
	}

	@Override
	public List<ItemStack> getItems(Random random) {
		List<ItemStack> ritems = new ArrayList<ItemStack>();
		final int length = chances.length;
		for (int i = 0; i < length; i++) {
			final int r = random.nextInt(1000+1);
			if (r <= chances[i]) ritems.add(items[i].getItemStack(random));
		}
		if (maxamount > -1)
		while(ritems.size() > maxamount) {
			final int r = random.nextInt(ritems.size());
			ritems.remove(r);
		}
		return ritems;
	}
	
	public LootGroup copy(int tickets) {
		return new LootGroup(items, chances, maxamount, tickets);
	}

	public int getMaxItems() {
		return maxamount;
	}

}
