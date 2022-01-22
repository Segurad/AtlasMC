package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Shulker;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.MathUtil;
import de.atlasmc.world.World;

public class CoreShulker extends CoreMob implements Shulker {

	protected static final MetaDataField<BlockFace>
	META_DIRECTION = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, BlockFace.DOWN, MetaDataType.DIRECTION);
	protected static final MetaDataField<Long>
	META_ATTACHMENT_POS = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, null, MetaDataType.OPT_POSITION);
	protected static final MetaDataField<Byte>
	META_SHIELD_HEIGHT = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_COLOR = new MetaDataField<>(CoreMob.LAST_META_INDEX+4, (byte) DyeColor.PURPLE.getID(), MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+4;
	
	public CoreShulker(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DIRECTION);
		metaContainer.set(META_ATTACHMENT_POS);
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
	public Location getAttachmentPosition(Location loc) {
		Long pos = metaContainer.getData(META_ATTACHMENT_POS);
		if (pos == null)
			return null;
		return MathUtil.getLocation(getWorld(), loc, pos);
	}
	
	@Override
	public Location getAttachmentPosition() {
		Long pos = metaContainer.getData(META_ATTACHMENT_POS);
		if (pos == null)
			return null;
		return MathUtil.getLocation(getWorld(), pos);
	}

	@Override
	public void setAttachmentPosition(SimpleLocation loc) {
		if (loc == null)
			metaContainer.get(META_ATTACHMENT_POS).setData(null);
		else
			setAttachmentPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	@Override
	public void setAttachmentPosition(int x, int y, int z) {
		metaContainer.get(META_ATTACHMENT_POS).setData(MathUtil.toPosition(x, y, z));		
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
		return DyeColor.getByID(metaContainer.getData(META_COLOR));
	}

	@Override
	public void setColor(DyeColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		metaContainer.get(META_COLOR).setData((byte) color.getID());
	}

}
