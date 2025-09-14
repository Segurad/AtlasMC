package de.atlasmc.core.node.entity;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.entity.BlockDisplay;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreBlockDisplay extends CoreDisplay implements BlockDisplay {

	protected static final MetaDataField<Integer> META_DISPLAYED_BLOCK = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, 0, MetaDataType.BLOCKSTATE);
	
	protected static final int LAST_META_INDEX = CoreDisplay.LAST_META_INDEX+1;
	
	private BlockData block;
	
	public CoreBlockDisplay(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DISPLAYED_BLOCK);
	}

	@Override
	public BlockData getBlockData() {
		return block;
	}

	@Override
	public void setBlockData(BlockData data) {
		int id = 0;
		if (data != null) {
			block = data;
			id = data.getStateID();
		}
		metaContainer.get(META_DISPLAYED_BLOCK).setData(id);
	}

	@Override
	public void setBlockDataType(BlockType type) {
		int id = 0;
		if (type == null)
			block = null;
		if (block == null || block.getType() != type) {
			block = type.createBlockData();
			id = block.getStateID();
		}
		metaContainer.get(META_DISPLAYED_BLOCK).setData(id);
	}

	@Override
	public BlockType getBlockDataType() {
		return block != null ? block.getType() : null;
	}

}
