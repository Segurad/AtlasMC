package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Lantern;

public class CoreLantern extends CoreWaterlogged implements Lantern {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, BlockDataProperty.HANGING);
	}
	
	protected boolean hanging;
	
	public CoreLantern(BlockType type) {
		super(type);
	}

	@Override
	public boolean isHanging() {
		return hanging;
	}

	@Override
	public void setHanging(boolean hanging) {
		this.hanging = hanging;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(hanging?0:2);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
