package de.atlasmc.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.factory.ClassFactory;

/**
 * Class based {@link TileEntityFactory}
 */
public class ClassTileEntityFactory extends ClassFactory<TileEntity> implements TileEntityFactory, ConfigurationSerializeable {
	
	private final Class<? extends TileEntity> tileInterface;
	private final int tileID;
	
	/**
	 * 
	 * @param tileInterface
	 * @param tile class must have a constructor ({@link Material})
	 */
	public <T extends TileEntity> ClassTileEntityFactory(Class<T> tileInterface, Class<? extends T> tile, int tileID) {
		super(tile, BlockType.class);
		if (tileInterface == null) 
			throw new IllegalArgumentException("TileInterface can not be null!");
		if (!tileInterface.isAssignableFrom(tile)) 
			throw new IllegalArgumentException("TileInterface is not assignable from Tile!");
		this.tileInterface = tileInterface;
		this.tileID = tileID;
	}
	
	public ClassTileEntityFactory(ConfigurationSection cfg) {
		this(getClass(cfg.getString("tileInterface")),getClass(cfg.getString("tile")), cfg.getInt("tileID"));
	}

	@Override
	public boolean isValidTile(TileEntity tile) {
		return tileInterface.isInstance(tile);
	}

	@Override
	public TileEntity createTile(BlockType type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		return create(type);
	}

	@Override
	public int getTileID() {
		return tileID;
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("tileInterface", tileInterface.getName());
		configuration.set("tile", clazz.getName());
		configuration.set("tileID", tileID);
		return configuration;
	}

}
