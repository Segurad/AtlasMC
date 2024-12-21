package de.atlascore.block.data.type;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.PistonHead;

public class CorePistonHead extends CoreTechnicalPiston implements PistonHead {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreTechnicalPiston.PROPERTIES, BlockDataProperty.SHORT);
	}
	
	private boolean _short;
	
	public CorePistonHead(Material material) {
		super(material);
	}

	@Override
	public boolean isShort() {
		return _short;
	}

	@Override
	public void setShort(boolean _short) {
		this._short = _short;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+getFaceValue()*4+getType().ordinal()+(_short?0:2);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
