package de.atlasmc.block.tile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.atlasmc.block.BlockType;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.factory.FactoryException;

/**
 * Class based {@link TileEntityFactory}
 */
public class ClassTileEntityFactory implements TileEntityFactory, ConfigurationSerializeable {
	
	private final Constructor<? extends TileEntity> constructor;
	private final Class<? extends TileEntity> tileInterface;
	private final Class<? extends TileEntity> tile;
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
		try {
			this.constructor = tile.getConstructor(BlockType.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new FactoryException("Error while fetching constructor for class: " + tile.getName(), e);
		}
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
	public TileEntity createTile(BlockType type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		try {
			return constructor.newInstance(type);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new FactoryException("Error while creating tile entity", e);
		}
	}

	@Override
	public int getTileID() {
		return tileID;
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("tileInterface", tileInterface.getName());
		configuration.set("tile", tile.getName());
		configuration.set("tileID", tileID);
		return configuration;
	}

}
