package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreRotatable;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Skull;

public class CoreSkull extends CoreRotatable implements Skull {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreRotatable.PROPERTIES, BlockDataProperty.POWERED);
	}
	
	private boolean powered;
	
	public CoreSkull(BlockType type) {
		super(type);
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
		return super.getStateID()+(powered?0:16);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
