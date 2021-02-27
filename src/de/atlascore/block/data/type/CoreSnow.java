package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Snow;
import de.atlasmc.util.Validate;

public class CoreSnow extends CoreBlockData implements Snow {

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
		Validate.isTrue(layers > 0 && layers < 9, "NamespaceID is not between 1 and 8: " + layers);
		this.layers = layers;
	}

	@Override
	public int getStateID() {
		return super.getStateID()+layers;
	}
}
