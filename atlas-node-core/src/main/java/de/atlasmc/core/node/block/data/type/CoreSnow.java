package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Snow;

public class CoreSnow extends CoreBlockData implements Snow {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.LAYERS);
	}
	
	private int layers;
	
	public CoreSnow(BlockType type) {
		super(type);
		layers = 1;
	}

	@Override
	public int getLayers() {
		return layers;
	}

	@Override
	public int getMaxLayers() {
		return 8;
	}

	@Override
	public int getMinLayers() {
		return 1;
	}

	@Override
	public void setLayers(int layers) {
		if (layers < 1 || layers > 8) 
			throw new IllegalArgumentException("NamespaceID is not between 1 and 8: " + layers);
		this.layers = layers;
	}

	@Override
	public int getStateID() {
		return super.getStateID()+(layers-1);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
