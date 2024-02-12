package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Levelled;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.configuration.Configuration;

/**
 * Class based {@link MetaDataFactory} for {@link Ageable} BlockData
 */
public class LevelledClassMetaDataFactory extends ClassMetaDataFactory {

	private final int maxlevel;
	
	public <I extends ItemMeta, L extends Levelled> LevelledClassMetaDataFactory(Class<I> metaInterface, Class<? extends I> meta, Class<L> dataInterface, Class<? extends L> data, int maxlevel) {
		super(metaInterface, meta, dataInterface, data);
		this.maxlevel = maxlevel;
	}
	
	public LevelledClassMetaDataFactory(Configuration cfg) throws ClassNotFoundException {
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
