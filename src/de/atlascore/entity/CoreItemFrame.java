package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ItemFrame;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreItemFrame extends CoreEntity implements ItemFrame {

	protected static final MetaDataField<ItemStack>
	META_FRAME_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Integer>
	META_FRAME_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_ITEM_DROP_CHANCE = "ItemDropChance",
	NBT_ITEM_ROTATION = "ItemRotation",
	NBT_ITEM = "Item",
//	NBT_TILE_X = "TileX", TODO unnecessary
//	NBT_TILE_Y = "TileY",
//	NBT_TILE_Z = "TileZ",
	NBT_FACING = "Facing",
	NBT_INVISIBLE = "Invisible",
	NBT_FIXED = "Fixed";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ITEM_DROP_CHANCE, (holder, reader) -> {
			if (holder instanceof ItemFrame) {
				((ItemFrame) holder).setItemDropChance(reader.readFloatTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ITEM_ROTATION, (holder, reader) -> {
			if (holder instanceof ItemFrame) {
				((ItemFrame) holder).setRotation(Rotation.getByID(reader.readByteTag()));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ITEM, (holder, reader) -> {
			if (!(holder instanceof ItemFrame)) {
				reader.skipTag();
				return;
			}
			reader.readNextEntry();
			Material mat = null;
			if (!NBT_ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(NBT_ID);
				mat = Material.getByName(reader.readStringTag());
				reader.reset();
			} else mat = Material.getByName(reader.readStringTag());
			ItemStack item = new ItemStack(mat);
			item.fromNBT(reader);
			((ItemFrame) holder).setItemStack(item);
		});
		NBT_FIELDS.setField(NBT_FACING, (holder, reader) -> {
			if (holder instanceof ItemFrame) {
				switch (reader.readByteTag()) {
				case 0:
					((ItemFrame) holder).setOrientation(BlockFace.DOWN);
					break;
				case 1:
					((ItemFrame) holder).setOrientation(BlockFace.UP);
					break;
				case 2:
					((ItemFrame) holder).setOrientation(BlockFace.NORTH);
					break;
				case 3:
					((ItemFrame) holder).setOrientation(BlockFace.SOUTH);
					break;
				case 4:
					((ItemFrame) holder).setOrientation(BlockFace.WEST);
					break;
				case 5:
					((ItemFrame) holder).setOrientation(BlockFace.EAST);
					break;
				default:
					break;
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_INVISIBLE, (holder, reader) -> {
			if (holder instanceof ItemFrame) {
				((ItemFrame) holder).setInvisible(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_FIXED, (holder, reader) -> {
			if (holder instanceof ItemFrame) {
				((ItemFrame) holder).setFixed(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private boolean fixed;
	private BlockFace orientation;
	private float dropChance = 1.0f;
	
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

	@Override
	public void setItemDropChance(float chance) {
		this.dropChance = chance;
	}

	@Override
	public float getItemDropChance() {
		return dropChance;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeFloatTag(NBT_ITEM_DROP_CHANCE, getItemDropChance());
		switch (getOrientation()) {
		case DOWN:
			writer.writeByteTag(NBT_FACING, 0);
			break;
		case UP:
			writer.writeByteTag(NBT_FACING, 1);
			break;
		case NORTH:
			writer.writeByteTag(NBT_FACING, 2);
			break;
		case SOUTH:
			writer.writeByteTag(NBT_FACING, 3);
			break;
		case WEST:
			writer.writeByteTag(NBT_FACING, 4);
			break;
		case EAST:
			writer.writeByteTag(NBT_FACING, 5);
			break;
		default:
			break;
		}
		if (getItem() != null) {
			writer.writeCompoundTag(NBT_ITEM);
			getItem().toNBT(writer, systemData);
			writer.writeEndTag();
		}
		writer.writeByteTag(NBT_ITEM_ROTATION, getRotation().getID());
		writer.writeByteTag(NBT_INVISIBLE, isInvisible());
		writer.writeByteTag(NBT_FIXED, isFixed());
		
	}

}
