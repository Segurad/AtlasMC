package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.DriedGhast;

public class HydrationProperty extends AbstractIntProperty {

	public HydrationProperty() {
		super("hydration");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof DriedGhast hydratable)
			hydratable.setHydration(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof DriedGhast hydatable)
			return hydatable.getHydration();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof DriedGhast;
	}

}
