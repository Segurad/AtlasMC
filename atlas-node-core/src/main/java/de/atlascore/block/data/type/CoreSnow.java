package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Snow;

public class CoreSnow extends CoreBlockData implements Snow {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.LAYERS);
	}
	
	private int layers;
	
	public CoreSnow(Material material) {
		super(material);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
