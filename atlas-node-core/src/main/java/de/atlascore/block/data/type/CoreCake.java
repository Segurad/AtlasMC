package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Cake;

public class CoreCake extends CoreBlockData implements Cake {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.BITES);
	}
	
	private int bites;
	
	public CoreCake(Material material) {
		super(material);
	}

	@Override
	public int getBites() {
		return bites;
	}

	@Override
	public int getMaxBites() {
		return 6;
	}

	@Override
	public void setBites(int bites) {
		if (bites < 0 || bites > 7)
			throw new IllegalArgumentException("Bites is not between 0 and 6: " + bites);
		this.bites = bites;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+bites;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
