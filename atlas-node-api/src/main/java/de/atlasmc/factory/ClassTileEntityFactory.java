package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.util.configuration.Configuration;

/**
 * Class based {@link TileEntityFactory}
 */
public class ClassTileEntityFactory implements TileEntityFactory {
	
	private final Class<? extends TileEntity> tileInterface, tile;
	private final int tileID;
	
	/**
	 * 
	 * @param tileInterface
	 * @param tile class must have a constructor ({@link Material})
	 */
	public <T extends TileEntity> ClassTileEntityFactory(Class<T> tileInterface, Class<? extends T> tile, int tileID) {
		if (tileInterface == null) 
			throw new IllegalArgumentException("TileInterface can not be null!");
		if (tile == null) 
			throw new IllegalArgumentException("Tile can not be null!");
		if (!tileInterface.isAssignableFrom(tile)) 
			throw new IllegalArgumentException("TileInterface is not assignable from Tile!");
		this.tile = tile;
		this.tileInterface = tileInterface;
		this.tileID = tileID;
	}
	
	@SuppressWarnings("unchecked")
	public ClassTileEntityFactory(Configuration cfg) throws ClassNotFoundException {
		this((Class<TileEntity>)Class.forName(cfg.getString("tileInterface")),(Class<TileEntity>) Class.forName(cfg.getString("tile")), cfg.getInt("tileID"));
	}

	@Override
	public boolean isValidTile(TileEntity tile) {
		return tileInterface.isInstance(tile);
	}

	@Override
	public TileEntity createTile(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) 
			throw new IllegalArgumentException("Material is not a Block!");
		try {
			return tile.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getTileID() {
		return tileID;
	}

}
