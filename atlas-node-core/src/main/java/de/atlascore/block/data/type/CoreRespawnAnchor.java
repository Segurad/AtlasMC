package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.RespawnAnchor;

public class CoreRespawnAnchor extends CoreBlockData implements RespawnAnchor {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.CHARGES);
	}
	
	private int charges;
	
	public CoreRespawnAnchor(BlockType type) {
		super(type);
	}

	@Override
	public int getCharges() {
		return charges;
	}

	@Override
	public int getMaxCharges() {
		return 4;
	}

	@Override
	public void setCharges(int charges) {
		if (charges >  4 || charges < 0) 
			throw new IllegalArgumentException("Charges is not between 0 and 4: " + charges);
		this.charges = charges;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+charges;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
