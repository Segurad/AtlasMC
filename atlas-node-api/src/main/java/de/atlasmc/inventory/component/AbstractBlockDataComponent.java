package de.atlasmc.inventory.component;

import java.util.Map;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.property.BlockDataProperty;

public interface AbstractBlockDataComponent extends ItemComponent {
	
	Map<BlockDataProperty<?>, Object> getProperties();
	
	void setProperties(Map<BlockDataProperty<?>, Object> properties);
	
	<T> void setProperty(BlockDataProperty<T> property, T value);
	
	boolean hasProperties();
	
	<T> T getProperty(BlockDataProperty<T> property);
	
	void applyTo(BlockData data);
	
	AbstractBlockDataComponent clone();

}
