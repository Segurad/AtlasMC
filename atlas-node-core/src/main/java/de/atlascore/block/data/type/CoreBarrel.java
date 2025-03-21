package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Barrel;

public class CoreBarrel extends CoreDirectional6Faces implements Barrel {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, BlockDataProperty.OPEN);
	}
	
	private boolean open;
	
	public CoreBarrel(BlockType type) {
		super(type);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(open?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
