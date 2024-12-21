package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.Attachable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreAttachable extends CoreBlockData implements Attachable {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.ATTACHED);
	}
	
	private boolean attached;
	
	public CoreAttachable(Material material) {
		super(material);
	}
	
	@Override
	public boolean isAttached() {
		return attached;
	}
	
	@Override
	public void setAttached(boolean attached) {
		this.attached = attached;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (attached ? 0 : 1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
