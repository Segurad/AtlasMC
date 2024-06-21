package de.atlasmc.inventory.meta;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.util.FactoryException;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;

/**
 * Class based {@link ItemMetaFactory} for Materials
 */
public class ClassItemMetaFactory implements ItemMetaFactory, ConfigurationSerializeable {
	
	protected final Class<? extends ItemMeta> metaInterface, meta;
	
	/**
	 * 
	 * @param metaInterface
	 * @param meta class must have a constructor ({@link Material})
	 */
	public <I extends ItemMeta> ClassItemMetaFactory(Class<I> metaInterface, Class<? extends I> meta) {
		if (metaInterface != null && !metaInterface.isAssignableFrom(meta)) 
			throw new IllegalArgumentException("MetaInterface is not assignable from Meta!");
		this.metaInterface = metaInterface;
		this.meta = meta;
	}
	
	@SuppressWarnings("unchecked")
	public ClassItemMetaFactory(Configuration cfg) throws ClassNotFoundException {
		if (cfg.contains("metaInterface")) {
			String val = cfg.getString("metaInterface");
			this.metaInterface = val == null ? ItemMeta.class : (Class<? extends ItemMeta>) Class.forName(val);
		} else this.metaInterface = null;
		if (cfg.contains("meta")) {
			String val = cfg.getString("meta");
			this.meta = (Class<? extends ItemMeta>) Class.forName(val);
		} else this.meta = null;
	}
	
	@Override
	public boolean isValidMeta(ItemMeta meta) {
		if (meta == null || metaInterface == null) 
			return false;
		return metaInterface.isInstance(meta);
	}
	
	@Override
	public ItemMeta createMeta(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isItem()) 
			throw new IllegalArgumentException("Material is not a Item!");
		if (meta == null) 
			return null;
		try {
			return meta.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new FactoryException("Error while creating meta", e);
		}
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		if (metaInterface != null)
			configuration.set("metaInterface", metaInterface.getName());
		if (meta != null)
			configuration.set("meta", meta.getName());
		return configuration;
	}
	
}
