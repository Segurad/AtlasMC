package de.atlasmc.block.tile;

import java.util.List;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.entity.Bee;

public interface Beehive extends TileEntity {
	
	public Location getFlowerPos();
	
	public Location getFlowerPos(Location loc);
	
	public void setFlowerPos(SimpleLocation loc);
	
	/**
	 * Returns a List containing all {@link Bee}s currently in this hive
	 * @return list of Bees
	 */
	public List<Bee> getBees();
	
	public void removeBee(Bee bee);
	
	public void addBee(Bee bee);
	
	public int getBeeCount();
	
	public void setFlowerPosX(int x);
	
	public void setFlowerPosY(int y);
	
	public void setFlowerPosZ(int z);

}
