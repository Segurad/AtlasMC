package de.atlascore.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.AbstractMinecart;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreAbstractMinecart extends CoreVehicle implements AbstractMinecart {

	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_ID = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_Y = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+2, 6, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_SHOW_CUSTOM_BLOCK = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreVehicle.LAST_META_INDEX+3;
	
	private BlockData customBlockData; 
	
	public CoreAbstractMinecart(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_CUSTOM_BLOCK_ID);
		metaContainer.set(META_CUSTOM_BLOCK_Y);
		metaContainer.set(META_SHOW_CUSTOM_BLOCK);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public BlockData getCustomBlock() {
		return customBlockData;
	}

	@Override
	public void setCustomBlock(BlockData data) {
		if (data == null)
			metaContainer.get(META_CUSTOM_BLOCK_ID).setData(0);
		else
			metaContainer.get(META_CUSTOM_BLOCK_ID).setData(data.getStateID());
		this.customBlockData = data;		
	}

	@Override
	public int getCustomBlockY() {
		return metaContainer.getData(META_CUSTOM_BLOCK_Y);
	}

	@Override
	public void setCustomBlockY(int y) {
		if (y < 0 || y > 16)
			throw new IllegalArgumentException("Y must be between 0 and 16: " + y);
		metaContainer.get(META_CUSTOM_BLOCK_Y).setData(y);
	}

	@Override
	public boolean getShowCustomBlock() {
		return metaContainer.getData(META_SHOW_CUSTOM_BLOCK);
	}

	@Override
	public void setShowCustomBlock(boolean show) {
		metaContainer.get(META_SHOW_CUSTOM_BLOCK).setData(show);		
	}

	@Override
	public boolean hasCustomBlock() {
		return customBlockData != null;
	}

	@Override
	public void setCustomBlockType(BlockType type) {
		if (type != null)
			setCustomBlock(type.createBlockData());
		else
			setCustomBlock(null);
	}

}
