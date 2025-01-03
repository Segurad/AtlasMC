package de.atlasmc.inventory.component;

import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.tag.NBT;

public interface CustomDataComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:custom_data");
	
	CustomDataComponent clone();
	
	void add(NBT nbt);
	
	void removeNBT(NBT nbt);
	
	void removeNBT(String key);
	
	Map<String, NBT> getValues();
	
	boolean hasValues();

}
