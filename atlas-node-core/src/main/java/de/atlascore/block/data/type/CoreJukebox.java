package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Jukebox;

public class CoreJukebox extends CoreBlockData implements Jukebox {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.HAS_RECORD);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
