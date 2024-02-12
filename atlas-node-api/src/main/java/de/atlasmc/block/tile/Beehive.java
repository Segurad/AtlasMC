package de.atlasmc.block.tile;

import java.util.List;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.entity.Bee;

public interface Beehive extends TileEntity {
	
	Location getFlowerPos();
	
	Location getFlowerPos(Location loc);
	
	void setFlowerPos(SimpleLocation loc);
	
	/**
	 * Returns a List containing all {@link Bee}s currently in this hive
	 * @return list of Bees
	 */
	List<Bee> getBees();
	
	void removeBee(Bee bee);
	
	void addBee(Bee bee);
	
	int getBeeCount();
	
	void setFlowerPosX(int x);
	
	void setFlowerPosY(int y);
	
	void setFlowerPosZ(int z);
	
	Beehive clone();

}
