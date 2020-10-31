package de.atlasmc.factory.metadata;

import java.lang.reflect.InvocationTargetException;

import de.atlascore.inventory.meta.CoreItemMeta;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.ItemMeta;

public class ClassMetaDataFactory implements MetaDataFactory {
	
	public static ClassMetaDataFactory DEFAULT;
	
	static {
		DEFAULT = new ClassMetaDataFactory(ItemMeta.class, CoreItemMeta.class, BlockData.class, null);
	}
	
	private Class<? extends ItemMeta> metaInterface, meta;
	private Class<? extends BlockData> dataInterface, data;
	
	public ClassMetaDataFactory(Class<? extends BlockData> dataInterface, Class<? extends BlockData> data) {
		this.dataInterface = dataInterface;
		this.metaInterface = ItemMeta.class;
		this.data = data;
		this.meta = CoreItemMeta.class;
	}
	
	public ClassMetaDataFactory(Class<? extends ItemMeta> metaInterface, Class<? extends ItemMeta> meta, Class<? extends BlockData> dataInterface, Class<? extends BlockData> data) {
		this.dataInterface = dataInterface;
		this.metaInterface = metaInterface;
		this.data = data;
		this.meta = meta;
	}
	
	public boolean isValidMeta(ItemMeta meta) {
		if (meta == null) return false;
		return meta.getClass().isAssignableFrom(metaInterface);
	}
	
	public boolean isValidData(BlockData data) {
		if (data == null) return false;
		return data.getClass().isAssignableFrom(dataInterface); 
	}
	
	public ItemMeta createMeta(Material material) {
		if (material == null) throw new IllegalArgumentException("Material can not be null!");
		if (!material.isItem()) throw new Error("Material is not a Item!");
		if (meta == null) return null;
		try {
			return meta.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BlockData createData(Material material) {
		if (material == null) throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) throw new Error("Material is not a Block!");
		if (data == null) return null;
		try {
			return data.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
