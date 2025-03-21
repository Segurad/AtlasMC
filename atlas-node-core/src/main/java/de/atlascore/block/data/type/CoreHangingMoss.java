package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.HangingMoss;

public class CoreHangingMoss extends CoreBlockData implements HangingMoss {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.TIP);
	}
	
	protected boolean tip;
	
	public CoreHangingMoss(BlockType type) {
		super(type);
		tip = true;
	}

	@Override
	public boolean isTip() {
		return tip;
	}

	@Override
	public void setTip(boolean tip) {
		this.tip = tip;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+(tip?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
