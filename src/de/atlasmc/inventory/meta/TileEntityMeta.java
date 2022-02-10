package de.atlasmc.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;

public interface TileEntityMeta extends ItemMeta {
	
	public void setTileEntity(TileEntity tile);
	
	public TileEntity getTileEntity();
	
	public boolean hasTileEntity();
	
	public Material getType();
	
	public TileEntityMeta clone();

}
