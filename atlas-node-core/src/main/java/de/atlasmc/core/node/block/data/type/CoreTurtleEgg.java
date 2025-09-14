package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreHatchable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.TurtleEgg;

public class CoreTurtleEgg extends CoreHatchable implements TurtleEgg {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreHatchable.PROPERTIES, BlockDataProperty.EGGS);
	}
	
	private int eggs;
	
	public CoreTurtleEgg(BlockType type) {
		super(type);
		eggs = 1;
	}

	@Override
	public int getEggs() {
		return eggs;
	}

	@Override
	public int getMaxEggs() {
		return 4;
	}

	@Override
	public int getMinEggs() {
		return 1;
	}

	@Override
	public void setEggs(int eggs) {
		if (eggs < 1 || eggs > 4) 
			throw new IllegalArgumentException("Eggs is not between 1 and 4: " + eggs);
		this.eggs = eggs;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(eggs-1)*3;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
