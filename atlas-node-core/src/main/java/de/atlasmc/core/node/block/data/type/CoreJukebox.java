package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Jukebox;

public class CoreJukebox extends CoreBlockData implements Jukebox {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.HAS_RECORD);
	}
	
	private boolean record;
	
	public CoreJukebox(BlockType type) {
		super(type);
	}

	@Override
	public boolean hasRecord() {
		return record;
	}

	@Override
	public void setRecord(boolean has) {
		this.record = has;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(record?0:1);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
