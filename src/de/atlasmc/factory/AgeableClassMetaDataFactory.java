package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.BlockData;

/**
 * Class based {@link MetaDataFactory} for {@link Ageable} BlockData
 */
public class AgeableClassMetaDataFactory extends ClassMetaDataFactory {

	private final int maxage;
	
	public <A extends Ageable> AgeableClassMetaDataFactory(Class<A> dataInterface, Class<? extends A> data, int maxage) {
		super(dataInterface, data);
		this.maxage = maxage;
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
			return data.getConstructor(Material.class, int.class).newInstance(material, maxage);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
