package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.LeafLitter;

public class CoreLeafLitter extends CoreDirectional4Faces implements LeafLitter {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, PropertyType.SEGMENT_AMOUNT);
	}
	
	private int amount = 1;
	
	public CoreLeafLitter(BlockType type) {
		super(type);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

	@Override
	public int getSegmentAmount() {
		return amount;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID() + amount + getFaceValue() * 4;
	}

	@Override
	public void setSegmentAmount(int amount) {
		if (amount < 1 || amount > 4)
			throw new IllegalArgumentException("Amount must be between 1 and 4: " + amount);
		this.amount = amount;
	}
	
}
