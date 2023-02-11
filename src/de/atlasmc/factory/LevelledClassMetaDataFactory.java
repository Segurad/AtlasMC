package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Levelled;

/**
 * Class based {@link MetaDataFactory} for {@link Ageable} BlockData
 */
public class LevelledClassMetaDataFactory extends ClassMetaDataFactory {

	private final int maxlevel;
	
	public <L extends Levelled> LevelledClassMetaDataFactory(Class<L> dataInterface, Class<? extends L> data, int maxlevel) {
		super(dataInterface, data);
		this.maxlevel = maxlevel;
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
