package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreAnaloguePowerable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.DaylightDetectore;

public class CoreDaylightDetector extends CoreAnaloguePowerable implements DaylightDetectore {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAnaloguePowerable.PROPERTIES, BlockDataProperty.INVERTED);
	}
	
	private boolean inverted;
	
	public CoreDaylightDetector(BlockType type) {
		super(type);
	}

	@Override
	public boolean isInverted() {
		return inverted;
	}

	@Override
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(inverted?0:16);
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
