package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Observer;

public class CoreObserver extends CoreDirectional6Faces implements Observer {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, BlockDataProperty.POWERED);
	}
	
	private boolean powered;
	
	public CoreObserver(BlockType type) {
		super(type, BlockFace.SOUTH);
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
		return getType().getBlockStateID()+
				(powered?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
