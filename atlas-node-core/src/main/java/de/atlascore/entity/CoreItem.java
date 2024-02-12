package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Item;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreItem extends CoreEntity implements Item {

	protected static final MetaDataField<ItemStack>
	META_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer<CoreItem> NBT_FIELDS;
	
	protected static final CharKey
	NBT_AGE = CharKey.literal("Age"),
	NBT_ITEM = CharKey.literal("Item"),
	NBT_OWNER = CharKey.literal("Owner"),
	NBT_PICKUP_DELAY = CharKey.literal("PickupDelay"),
	NBT_THROWER = CharKey.literal("Thrower");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			holder.setLifeTime(reader.readShortTag());
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
		NBT_FIELDS.setField(NBT_OWNER, (holder, reader) -> {
			holder.setOwner(reader.readUUID());
		});
		NBT_FIELDS.setField(NBT_PICKUP_DELAY, (holder, reader) -> {
			holder.setPickupDelay(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_THROWER, (holder, reader) -> {
			holder.setThrower(reader.readUUID());
		});
	}
	
	private short age;
	private short pickupDelay;
	private UUID owner;
	private UUID thrower;
	
	public CoreItem(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreItem> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ITEM);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public ItemStack getItemStack() {
		return metaContainer.getData(META_ITEM);
	}

	@Override
	public void setItemStack(ItemStack item) {
		MetaData<ItemStack> data = metaContainer.get(META_ITEM);
		data.setData(item);
		data.setChanged(true);
	}

	@Override
	public int getPickupDelay() {
		return pickupDelay;
	}

	@Override
	public void setPickupDelay(int delay) {
		this.pickupDelay = (short) delay;
	}

	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public void setOwner(UUID uuid) {
		this.owner = uuid;
	}

	@Override
	public UUID getThrower() {
		return thrower;
	}

	@Override
	public void setThrower(UUID uuid) {
		this.thrower = uuid;
	}

	@Override
	public void setLifeTime(int ticks) {
		this.age = (short) ticks;
	}

	@Override
	public int getLifeTime() {
		return age;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeShortTag(NBT_AGE, LAST_META_INDEX);
		if (getItemStack() != null) {
			writer.writeCompoundTag(NBT_ITEM);
			getItemStack().toNBT(writer, systemData);
			writer.writeEndTag();
		}
		if (getOwner() != null)
			writer.writeUUID(NBT_OWNER, getOwner());
		writer.writeShortTag(NBT_PICKUP_DELAY, getPickupDelay());
		if (getThrower() != null)
			writer.writeUUID(NBT_THROWER, getThrower());
	}

}
