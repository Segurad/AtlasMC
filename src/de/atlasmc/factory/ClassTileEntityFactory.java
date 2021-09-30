package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;

/**
 * Class based {@link TileEntityFactory}
 */
public class ClassTileEntityFactory extends TileEntityFactory {
	
	private final Class<? extends TileEntity> tileInterface, tile;
	
	private int hash;
	
	/**
	 * 
	 * @param tileInterface
	 * @param tile class must have a constructor ({@link Material})
	 */
	public ClassTileEntityFactory(Class<? extends TileEntity> tileInterface, Class<? extends TileEntity> tile) {
		if (tileInterface == null) throw new IllegalArgumentException("TileInterface can not be null!");
		if (tile == null) throw new IllegalArgumentException("Tile can not be null!");
		if (!tileInterface.isAssignableFrom(tile)) throw new IllegalArgumentException("TileInterface is not assignable from Tile!");
		this.tile = tile;
		this.tileInterface = tileInterface;
	}

	@Override
	public boolean isValidTile(TileEntity tile) {
		return tileInterface.isInstance(tile);
	}

	@Override
	public TileEntity createTile(Material material, boolean preConfig) {
		if (material == null) throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) throw new IllegalArgumentException("Material is not a Block!");
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

	@Override
	public int hashCode() {
		if (hash != 0)
			return hash;
		final int prime = 31;
		hash = 1;
		hash = prime * hash + ((tile == null) ? 0 : tile.hashCode());
		hash = prime * hash + ((tileInterface == null) ? 0 : tileInterface.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassTileEntityFactory other = (ClassTileEntityFactory) obj;
		if (tile == null) {
			if (other.tile != null)
				return false;
		} else if (!tile.equals(other.tile))
			return false;
		if (tileInterface == null) {
			if (other.tileInterface != null)
				return false;
		} else if (!tileInterface.equals(other.tileInterface))
			return false;
		return true;
	} 

}
