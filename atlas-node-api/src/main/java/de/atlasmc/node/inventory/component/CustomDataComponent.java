package de.atlasmc.node.inventory.component;

import java.util.Map;

import de.atlasmc.util.nbt.tag.NBT;

public interface CustomDataComponent extends ItemComponent {
	
	CustomDataComponent clone();
	
	void add(NBT nbt);
	
	void removeNBT(NBT nbt);
	
	void removeNBT(String key);
	
	Map<String, NBT> getValues();
	
	boolean hasValues();

}
