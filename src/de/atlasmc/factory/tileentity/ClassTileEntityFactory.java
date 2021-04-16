package de.atlasmc.factory.tileentity;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.util.Validate;

public class ClassTileEntityFactory extends TileEntityFactory {
	
	private final Class<? extends TileEntity> tileInterface, tile;
	
	/**
	 * 
	 * @param tileInterface
	 * @param tile class must have a constructor with Material
	 */
	public ClassTileEntityFactory(Class<? extends TileEntity> tileInterface, Class<? extends TileEntity> tile) {
		Validate.notNull(tileInterface, "TileInterface can not be null!");
		Validate.notNull(tile, "Tile can not be null!");
		this.tile = tile;
		this.tileInterface = tileInterface;
	}

	@Override
	public boolean isValidTile(TileEntity tile) {
		return tileInterface.isInstance(tile);
	}

	@Override
	public TileEntity createTile(Material material, boolean preConfig) {
		Validate.notNull(material, "Material can not be null!");
		Validate.isTrue(material.isBlock(), "Material is not a Block!");
		if (preConfig) {
			TileEntity te = getPreConfig(material);
			if (te != null) return te.clone();
		}
		try {
			return tile.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	} 

}
