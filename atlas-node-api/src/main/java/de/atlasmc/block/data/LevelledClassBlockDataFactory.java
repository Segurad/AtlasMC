package de.atlasmc.block.data;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.util.configuration.Configuration;

/**
 * Class based {@link ItemMetaFactory} for {@link Ageable} BlockData
 */
public class LevelledClassBlockDataFactory extends ClassBlockDataFactory {

	private final int maxlevel;
	
	public <L extends Levelled> LevelledClassBlockDataFactory(Class<L> dataInterface, Class<? extends L> data, int maxlevel) {
		super(dataInterface, data);
		this.maxlevel = maxlevel;
	}
	
	public LevelledClassBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
		super(cfg);
		maxlevel = cfg.getInt("maxlevel");
	}
	
	@Override
	public BlockData createData(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) 
			throw new IllegalArgumentException("Material is not a Block!");
		if (data == null) 
			return null;
		try {
			return data.getConstructor(Material.class, int.class).newInstance(material, maxlevel);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
