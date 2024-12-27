package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreHightConnectable;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.MossyCarpet;

public class CoreMossyCarpet extends CoreHightConnectable implements MossyCarpet {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreHightConnectable.PROPERTIES, BlockDataProperty.BOTTOM);
	}
	
	protected boolean bottom;
	
	public CoreMossyCarpet(Material material) {
		super(material);
		bottom = true;
	}

	@Override
	public boolean isBottom() {
		return bottom;
	}

	@Override
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(bottom?0:81);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
