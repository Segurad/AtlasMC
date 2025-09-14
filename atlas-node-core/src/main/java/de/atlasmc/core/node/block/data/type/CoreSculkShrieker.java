package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterlogged;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.SculkShrieker;

public class CoreSculkShrieker extends CoreWaterlogged implements SculkShrieker {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, 
				BlockDataProperty.CAN_SUMMON,
				BlockDataProperty.SHRIEKING);
	}
	
	private boolean canSummon;
	private boolean shrieking;
	
	public CoreSculkShrieker(BlockType type) {
		super(type);
	}

	@Override
	public boolean canSummon() {
		return canSummon;
	}

	@Override
	public void setCanSummon(boolean canSummon) {
		this.canSummon = canSummon;
	}

	@Override
	public boolean isShrieking() {
		return shrieking;
	}

	@Override
	public void setShrieking(boolean shrieking) {
		this.shrieking = shrieking;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (shrieking?0:2) + (canSummon?0:4);
	}
	
	@Override
	public SculkShrieker clone() {
		return (SculkShrieker) super.clone();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
