package de.atlasmc.block.data;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.util.FactoryException;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;

/**
 * Class based {@link ItemMetaFactory} for {@link Ageable} BlockData
 */
public class AgeableClassBlockDataFactory extends ClassBlockDataFactory {

	private final int maxage;
	
	public <A extends Ageable> AgeableClassBlockDataFactory(Class<A> dataInterface, Class<? extends A> data, int maxage) {
		super(dataInterface, data);
		this.maxage = maxage;
	}
	
	public AgeableClassBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
		super(cfg);
		maxage = cfg.getInt("maxage");
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
			throw new FactoryException("Error while creating data", e);
		}
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("maxage", maxage);
		return super.toConfiguration(configuration);
	}

}
