package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ItemFrame;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.world.World;

public class CoreItemFrame extends CoreEntity implements ItemFrame {

	protected static final MetaDataField<ItemStack>
	META_FRAME_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Integer>
	META_FRAME_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	private boolean fixed;
	private BlockFace orientation;
	
	public CoreItemFrame(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
		orientation = BlockFace.NORTH;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<>(META_FRAME_ITEM));
		metaContainer.set(new MetaData<>(META_FRAME_ROTATION));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_FRAME_ITEM);
	}

	@Override
	public void setItemStack(ItemStack item) {
		MetaData<ItemStack> data =metaContainer.get(META_FRAME_ITEM);
		data.setData(item);
		data.setChanged(true);
	}

	@Override
	public void setItemStack(ItemStack item, boolean playSound) {
		setItemStack(item);
		if (playSound)
			causeSound(item != null ? Sound.ENTITY_ITEM_FRAME_ADD_ITEM :
				Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.MASTER, 1.0f, 1.0f);
	}

	@Override
	public Rotation getRotation() {
		return Rotation.getByID(metaContainer.getData(META_FRAME_ROTATION));
	}

	@Override
	public void setRotation(Rotation rotation) {
		metaContainer.get(META_FRAME_ROTATION).setData(rotation.getID());
	}

	@Override
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}

	@Override
	public BlockFace getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(BlockFace orientation) {
		switch (orientation) {
		case DOWN:
		case EAST:
		case NORTH:
		case SOUTH:
		case UP:
		case WEST:
			break;
		default:
			throw new IllegalArgumentException("Orientation not valid: " + orientation.name());
		}
		this.orientation = orientation;
	}

}
