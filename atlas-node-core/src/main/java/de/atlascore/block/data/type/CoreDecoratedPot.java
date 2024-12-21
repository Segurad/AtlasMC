package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.DecoratedPot;

public class CoreDecoratedPot extends CoreWaterloggedDirectional4Faces implements DecoratedPot {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, BlockDataProperty.CRACKED);
	}
	
	private boolean cracked;
	
	public CoreDecoratedPot(Material material) {
		super(material);
	}

	@Override
	public boolean isCracked() {
		return cracked;
	}

	@Override
	public void setCracked(boolean cracked) {
		this.cracked = cracked;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (isWaterlogged()?0:1) + (getFaceValue()*2) + (cracked?0:8);
	}
	
	@Override
	public CoreDecoratedPot clone() {
		return (CoreDecoratedPot) super.clone();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
