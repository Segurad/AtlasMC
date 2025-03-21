package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreAxisOrientable;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.CreakingHeart;

public class CoreCreakingHeart extends CoreAxisOrientable implements CreakingHeart {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAxisOrientable.PROPERTIES, 
				BlockDataProperty.ACTIVE,
				BlockDataProperty.NATURAL);
	}
	
	protected boolean active;
	protected boolean natural;
	
	public CoreCreakingHeart(BlockType type) {
		super(type);
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean isNatural() {
		return natural;
	}

	@Override
	public void setNatural(boolean natural) {
		this.natural = natural;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+(natural?0:1)+axis.ordinal()*2+(active?0:6);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
