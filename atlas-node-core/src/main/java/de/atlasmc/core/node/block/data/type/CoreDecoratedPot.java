package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.DecoratedPot;

public class CoreDecoratedPot extends CoreWaterloggedDirectional4Faces implements DecoratedPot {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, PropertyType.CRACKED);
	}
	
	private boolean cracked;
	
	public CoreDecoratedPot(BlockType type) {
		super(type);
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
		return getType().getBlockStateID() + (isWaterlogged()?0:1) + (getFaceValue()*2) + (cracked?0:8);
	}
	
	@Override
	public CoreDecoratedPot clone() {
		return (CoreDecoratedPot) super.clone();
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
