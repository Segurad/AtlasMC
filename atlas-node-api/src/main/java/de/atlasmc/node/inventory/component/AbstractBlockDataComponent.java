package de.atlasmc.node.inventory.component;

import java.util.Map;

import de.atlasmc.node.block.data.BlockData;

public interface AbstractBlockDataComponent extends ItemComponent {
	
	Map<String, String> getProperties();
	
	boolean hasProperties();
	
	void applyTo(BlockData data);

}
