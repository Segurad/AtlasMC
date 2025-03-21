package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.TNT;

public class CoreTNT extends CoreBlockData implements TNT {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.UNSTABLE);
	}
	
	private boolean unstable;
	
	public CoreTNT(BlockType type) {
		super(type);
	}

	@Override
	public boolean isUnstable() {
		return unstable;
	}

	@Override
	public void setUnstable(boolean stable) {
		this.unstable = stable;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(unstable?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
