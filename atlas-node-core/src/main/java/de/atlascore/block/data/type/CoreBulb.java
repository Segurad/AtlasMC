package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreLightable;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Bulb;

public class CoreBulb extends CoreLightable implements Bulb {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreLightable.PROPERTIES, BlockDataProperty.POWERED);
	}
	
	protected boolean powered;
	
	public CoreBulb(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (lit?0:1) + (powered?0:2);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
