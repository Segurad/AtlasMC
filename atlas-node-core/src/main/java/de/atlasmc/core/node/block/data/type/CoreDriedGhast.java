package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.DriedGhast;

public class CoreDriedGhast extends CoreWaterloggedDirectional4Faces implements DriedGhast {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, PropertyType.HYDRATION);
	}
	
	private int hydration;
	
	public CoreDriedGhast(BlockType type) {
		super(type);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

	@Override
	public int getHydration() {
		return hydration;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID() + (waterlogged ? 0 : 1) + hydration * 2 + getFaceValue() * 8;
	}

	@Override
	public void setHydration(int hydration) {
		if (hydration < 0 || hydration > 3)
			throw new IllegalArgumentException("Hydration must be between 0 and 3: " + hydration);
		this.hydration = hydration;
	}

}
