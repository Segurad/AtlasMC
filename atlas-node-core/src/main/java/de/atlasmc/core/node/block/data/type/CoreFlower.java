package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.Flower;

public class CoreFlower extends CoreDirectional4Faces implements Flower {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, BlockDataProperty.FLOWER_AMOUNT);
	}
	
	private int flowerAmount;
	
	public CoreFlower(BlockType type) {
		super(type);
		flowerAmount = 1;
	}
	
	@Override
	public CoreFlower clone() {
		return (CoreFlower) super.clone();
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
