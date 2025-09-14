package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.SculkCatalyst;

public class CoreSculkCatalyst extends CoreBlockData implements SculkCatalyst {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.BLOOM);
	}
	
	private boolean bloom;
	
	public CoreSculkCatalyst(BlockType type) {
		super(type);
	}

	@Override
	public boolean isBloom() {
		return bloom;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (bloom?0:1);
	}

	@Override
	public void setBloom(boolean bloom) {
		this.bloom = bloom;
	}
	
	@Override
	public CoreSculkCatalyst clone() {
		return (CoreSculkCatalyst) super.clone();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
