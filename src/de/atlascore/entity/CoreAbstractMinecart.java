package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.AbstractMinecart;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreAbstractMinecart extends CoreEntity implements AbstractMinecart {

	protected static final MetaDataField<Integer>
	META_SHAKING_POWER = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer> // TODO research direction values
	META_SHAKING_DIRECTION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 1, MetaDataType.INT);
	protected static final MetaDataField<Float>
	META_SHAKING_MULTIPLIER = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, 0.0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_ID = new MetaDataField<>(CoreEntity.LAST_META_INDEX+4, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_Y = new MetaDataField<>(CoreEntity.LAST_META_INDEX+5, 6, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_SHOW_CUSTOM_BLOCK = new MetaDataField<>(CoreEntity.LAST_META_INDEX+6, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+6;
	
	private BlockData customBlockData; 
	
	public CoreAbstractMinecart(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SHAKING_POWER);
		metaContainer.set(META_SHAKING_DIRECTION);
		metaContainer.set(META_SHAKING_MULTIPLIER);
		metaContainer.set(META_CUSTOM_BLOCK_ID);
		metaContainer.set(META_CUSTOM_BLOCK_Y);
		metaContainer.set(META_SHOW_CUSTOM_BLOCK);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getShakingPower() {
		return metaContainer.getData(META_SHAKING_POWER);
	}

	@Override
	public void setShakingPower(int power) {
		if (power < 0)
			throw new IllegalArgumentException("Power can not be lower than 0: " + power);
		metaContainer.get(META_SHAKING_POWER).setData(power);		
	}

	@Override
	public int getShakingDirection() {
		return metaContainer.getData(META_SHAKING_DIRECTION);
	}

	@Override
	public void setShakingDirection(int direction) {
		metaContainer.get(META_SHAKING_DIRECTION).setData(direction);
	}

	@Override
	public float getShakingMultiplier() {
		return metaContainer.getData(META_SHAKING_MULTIPLIER);
	}

	@Override
	public void setShakingMultiplier(float multiplier) {
		metaContainer.get(META_SHAKING_MULTIPLIER).setData(multiplier);		
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

}
