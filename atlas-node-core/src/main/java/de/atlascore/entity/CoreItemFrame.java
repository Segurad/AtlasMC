package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ItemFrame;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreItemFrame extends CoreHanging implements ItemFrame {

	protected static final MetaDataField<ItemStack>
	META_FRAME_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Integer>
	META_FRAME_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	protected static final NBTFieldContainer<CoreItemFrame> NBT_FIELDS;
	
	protected static final CharKey
	NBT_ITEM_DROP_CHANCE = CharKey.literal("ItemDropChance"),
	NBT_ITEM_ROTATION = CharKey.literal("ItemRotation"),
	NBT_ITEM = CharKey.literal("Item"),
	NBT_INVISIBLE = CharKey.literal("Invisible"),
	NBT_FIXED = CharKey.literal("Fixed");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_ITEM_DROP_CHANCE, (holder, reader) -> {
			holder.setItemDropChance(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_ITEM_ROTATION, (holder, reader) -> {
			holder.setRotation(Rotation.getByID(reader.readByteTag()));
		});
		NBT_FIELDS.setField(NBT_ITEM, (holder, reader) -> {
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
			holder.setItemStack(item);
		});
		NBT_FIELDS.setField(NBT_INVISIBLE, (holder, reader) -> {
			holder.setInvisible(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_FIXED, (holder, reader) -> {
			holder.setFixed(reader.readByteTag() == 1);
		});
	}
	
	private boolean fixed;
	private float dropChance = 1.0f;
	
	public CoreItemFrame(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreItemFrame> getFieldContainerRoot() {
		return NBT_FIELDS;
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
				Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.MASTER, 1.0f, 1.0f, 0); // TODO random seeding
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
