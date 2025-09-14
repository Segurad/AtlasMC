package de.atlasmc.core.node.entity;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Villager;
import de.atlasmc.node.inventory.ItemStack;

public class CoreVillager extends CoreAbstractVillager implements Villager {

	private List<ItemStack> pocket;
	private boolean willing;
	private long lastRestock;
	private int restocksToday;
	
	public CoreVillager(EntityType type) {
		super(type);
	}

	@Override
	public List<ItemStack> getPocketItems() {
		if (pocket == null)
			pocket = new ArrayList<>();
		return pocket;
	}

	@Override
	public boolean hasPocketItems() {
		return pocket != null && !pocket.isEmpty();
	}

	@Override
	public boolean isWilling() {
		return willing;
	}

	@Override
	public void setWilling(boolean willing) {
		this.willing = willing;
	}
	
	@Override
	public void setLastRestock(long tick) {
		this.lastRestock = tick;
	}

	@Override
	public long getLastRestock() {
		return lastRestock;
	}

	@Override
	public int getRestocksToday() {
		return restocksToday;
	}

	@Override
	public void setRestocksToday(int restocks) {
		this.restocksToday = restocks;
	}
	
}
