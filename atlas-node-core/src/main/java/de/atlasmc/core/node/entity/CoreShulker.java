package de.atlasmc.core.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Shulker;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.util.enums.EnumUtil;

public class CoreShulker extends CoreMob implements Shulker {

	protected static final MetaDataField<BlockFace>
	META_DIRECTION = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, BlockFace.DOWN, MetaDataType.DIRECTION);
	protected static final MetaDataField<Byte>
	META_SHIELD_HEIGHT = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_COLOR = new MetaDataField<>(CoreMob.LAST_META_INDEX+4, (byte) 16, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;
	
	public CoreShulker(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DIRECTION);
		metaContainer.set(META_SHIELD_HEIGHT);
		metaContainer.set(META_COLOR);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public BlockFace getAttachedFace() {
		return metaContainer.getData(META_DIRECTION);
	}

	@Override
	public void setAttachedFace(BlockFace attached) {
		metaContainer.get(META_DIRECTION).setData(attached);
	}

	@Override
	public int getShieldHeight() {
		return metaContainer.getData(META_SHIELD_HEIGHT);
	}

	@Override
	public void setShieldHeight(int height) {
		if (height < 0 || height > 100)
			throw new IllegalArgumentException("Height must be between 0 and 100: " + height);
		metaContainer.get(META_SHIELD_HEIGHT).setData((byte) height);
	}

	@Override
	public DyeColor getColor() {
		int color = metaContainer.getData(META_COLOR);
		return color == 16 ? null : EnumUtil.getByID(DyeColor.class, color);
	}

	@Override
	public void setColor(DyeColor color) {
		int value = 16;
		if (color != null)
			value = color.getID();
		metaContainer.get(META_COLOR).setData((byte) value);
	}
	
}
