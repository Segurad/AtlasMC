package de.atlasmc.inventory.meta;

import de.atlasmc.SimpleLocation;

public interface CompassMeta extends ItemMeta {
	
	public CompassMeta clone();
	
	@Override
	public default Class<? extends CompassMeta> getInterfaceClass() {
		return CompassMeta.class;
	}
	
	public SimpleLocation getLodestone();
	
	public boolean hasLodestone();
	
	public boolean isLodestoneTracked();
	
	public void setLodestone(SimpleLocation lodestone);
	
	public void setLodestoneTracked(boolean tracked);

}
