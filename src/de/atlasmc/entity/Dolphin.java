package de.atlasmc.entity;

import de.atlasmc.SimpleLocation;

public interface Dolphin extends WaterAnimal {
	
	public default SimpleLocation getTreasurePosition() {
		return getTreasurePosition(new SimpleLocation());
	}
	
	public SimpleLocation getTreasurePosition(SimpleLocation loc);
	
	public default void setTreasurePosition(SimpleLocation loc) {
		setTreasurePosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public void setTreasurePosition(int x, int y, int z);
	
	public boolean hasFish();
	
	public void setFish(boolean fish);
	
	public int getMoistureLevel();
	
	public void setMoistureLevel(int level);
	
	public int getMaxMoistureLevel();

	public boolean canPickupLoot();
	
	public void setCanPickupLoot(boolean canPickup);

}
