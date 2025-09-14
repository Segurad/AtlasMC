package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.DecoratedPot;

public class CoreDecoratedPot extends CoreWaterloggedDirectional4Faces implements DecoratedPot {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, BlockDataProperty.CRACKED);
	}
	
	private boolean cracked;
	
	public CoreDecoratedPot(BlockType type) {
		super(type);
	}

	@Override
	public boolean isCracked() {
		return cracked;
	}

	@Override
	public void setCracked(boolean cracked) {
		this.cracked = cracked;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID() + (isWaterlogged()?0:1) + (getFaceValue()*2) + (cracked?0:8);
	}
	
	@Override
	public CoreDecoratedPot clone() {
		return (CoreDecoratedPot) super.clone();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
