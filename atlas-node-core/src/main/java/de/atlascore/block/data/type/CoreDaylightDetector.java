package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.DaylightDetectore;

public class CoreDaylightDetector extends CoreAnaloguePowerable implements DaylightDetectore {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAnaloguePowerable.PROPERTIES, BlockDataProperty.INVERTED);
	}
	
	private boolean inverted;
	
	public CoreDaylightDetector(Material material) {
		super(material);
	}

	@Override
	public boolean isInverted() {
		return inverted;
	}

	@Override
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(inverted?0:16);
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
