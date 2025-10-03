package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.DriedGhast;

public class HydrationProperty extends AbstractIntProperty {

	public HydrationProperty() {
		super("hydration");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof DriedGhast hydratable)
			hydratable.setHydration(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof DriedGhast hydatable)
			return hydatable.getHydration();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof DriedGhast;
	}

}
