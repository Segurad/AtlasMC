package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.TNT;

public class CoreTNT extends CoreBlockData implements TNT {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.UNSTABLE);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
