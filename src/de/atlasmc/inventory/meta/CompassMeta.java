package de.atlasmc.inventory.meta;

import de.atlasmc.Location;

public interface CompassMeta extends ItemMeta {
	
	public CompassMeta clone();
	public Location getLodestone();
	public boolean hasLodestone();
	public boolean isLodestoneTracked();
	public void setLodestone(Location lodestone);
	public void setLodestoneTracked(boolean tracked);

}
