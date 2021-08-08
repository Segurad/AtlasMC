package de.atlasmc.factory.metadata;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.BlockData;

public class AgeableClassMetaDataFactory extends ClassMetaDataFactory {

	private final int maxage;
	
	public AgeableClassMetaDataFactory(Class<? extends Ageable> dataInterface, Class<? extends Ageable> data, int maxage) {
		super(dataInterface, data);
		this.maxage = maxage;
	}
	
	public BlockData createData(Material material, boolean preConfig) {
		if (material == null) throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) throw new IllegalArgumentException("Material is not a Block!");
		if (preConfig) {
			BlockData bd = MetaDataFactory.getDataPreConfig(material);
			if (bd != null) return bd.clone();
		}
		if (data == null) return null;
		try {
			return data.getConstructor(Material.class, int.class).newInstance(material, maxage);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
