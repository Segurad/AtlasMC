package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.CaveVinesPlant;

public class CoreCaveVinesPlant extends CoreBlockData implements CaveVinesPlant {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.BERRIES);
	}
	
	private boolean berries;
	
	public CoreCaveVinesPlant(BlockType type) {
		super(type);
	}

	@Override
	public boolean hasBerries() {
		return berries;
	}

	@Override
	public void setBerries(boolean berries) {
		this.berries = berries;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (berries?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
