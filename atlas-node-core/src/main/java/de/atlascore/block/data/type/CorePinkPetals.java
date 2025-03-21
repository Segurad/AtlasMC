package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.PinkPetals;

public class CorePinkPetals extends CoreDirectional4Faces implements PinkPetals {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, BlockDataProperty.FLOWER_AMOUNT);
	}
	
	private int flowerAmount;
	
	public CorePinkPetals(BlockType type) {
		super(type);
		flowerAmount = 1;
	}
	
	@Override
	public CorePinkPetals clone() {
		return (CorePinkPetals) super.clone();
	}

	@Override
	public int getFlowerAmount() {
		return flowerAmount;
	}

	@Override
	public void setFlowerAmount(int amount) {
		if (amount < 1 || amount > 4)
			throw new IllegalArgumentException("Amount must be between 1 and 4: " + amount);
		this.flowerAmount = amount;
	}

	@Override
	public int getMaxFlowerAmount() {
		return 4;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID() + (flowerAmount-1) + getFaceValue()*4;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
