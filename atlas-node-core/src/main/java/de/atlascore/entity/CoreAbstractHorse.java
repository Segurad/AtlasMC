package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.AbstractHorse;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractHorse extends CoreAgeableMob implements AbstractHorse {

	/**
	 * 0x02 - Is Tame<br>
	 * 0x04 - Is saddled<br>
	 * 0x08 - can bred<br>
	 * 0x10 - Is eating<br>
	 * 0x20 - Is rearing<br>
	 * 0x40 - Is mouth open
	 */
	protected static final MetaDataField<Byte>
	META_HORSE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<UUID>
	META_HORSE_OWNER = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, null, MetaDataType.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	protected static final NBTFieldSet<CoreAbstractHorse> NBT_FIELDS;
	
	protected static final CharKey
	NBT_BRED = CharKey.literal("Bred"),
	NBT_EATING_HAYSTACK = CharKey.literal("EatingHaystack"),
	NBT_OWNER = CharKey.literal("Owner"),
	NBT_SADDLE_ITEM = CharKey.literal("SaddleItem"),
	NBT_TAME = CharKey.literal("Tame");
	//NBT_TEMPER = "Temper"; TODO unnecessary (needed for taming)
	
	static {
		NBT_FIELDS = CoreAgeableMob.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_BRED, (holder, reader) -> {
			holder.setCanBred(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_EATING_HAYSTACK, (holder, reader) -> {
			holder.setEating(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_OWNER, (holder, reader) -> {
			holder.setOwner(reader.readUUID());
		});
		NBT_FIELDS.setField(NBT_SADDLE_ITEM, (holder, reader) -> {
			reader.readNextEntry();
			ItemStack item = ItemStack.getFromNBT(reader);
			holder.getInventory().setSaddle(item);
		});
		NBT_FIELDS.setField(NBT_TAME, (holder, reader) -> {
			holder.setTamed(reader.readByteTag() == 1);
		});
	}
	
	protected AbstractHorseInventory inv;
	
	public CoreAbstractHorse(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAbstractHorse> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HORSE_FLAGS);
		metaContainer.set(META_HORSE_OWNER);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isSaddled() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public boolean canBred() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x08) == 0x08;
	}

	@Override
	public boolean isEating() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x10) == 0x10;
	}

	@Override
	public boolean isRearing() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x20) == 0x20;
	}

	@Override
	public boolean isMouthOpen() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x40) == 0x40;
	}

	@Override
	public UUID getOwner() {
		return metaContainer.getData(META_HORSE_OWNER);
	}

	@Override
	public boolean isTamed() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x02) == 0x02;
	}

	@Override
	public void setTamed(boolean tamed) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (tamed ? data.getData() | 0x02 : data.getData() & 0xFD));
	}

	@Override
	public void setSaddled(boolean saddled) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (saddled ? data.getData() | 0x04 : data.getData() & 0xFB));
	}

	@Override
	public void setCanBred(boolean bred) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (bred ? data.getData() | 0x08 : data.getData() & 0xF7));
	}

	@Override
	public void setEating(boolean eating) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (eating ? data.getData() | 0x10 : data.getData() & 0xEF));
	}

	@Override
	public void setRearing(boolean rearing) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (rearing ? data.getData() | 0x20 : data.getData() & 0xDF));
	}

	@Override
	public void setMouthOpen(boolean open) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (open ? data.getData() | 0x40 : data.getData() & 0xBF));
	}

	@Override
	public void setOwner(UUID owner) {
		metaContainer.get(META_HORSE_OWNER).setData(owner);
	}

	@Override
	public AbstractHorseInventory getInventory() {
		if (inv == null)
			inv = createInventory();
		return inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}
	
	protected abstract AbstractHorseInventory createInventory();
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (canBred())
			writer.writeByteTag(NBT_BRED, true);
		if (isEating())
			writer.writeByteTag(NBT_EATING_HAYSTACK, true);
		if (getOwner() != null)
			writer.writeUUID(NBT_OWNER, getOwner());
		if (inv != null && getInventory().getSaddle() != null) {
			writer.writeCompoundTag(NBT_SADDLE_ITEM);
			getInventory().getSaddle().toNBT(writer, systemData);
			writer.writeEndTag();
		}
		if (isTamed())
			writer.writeByteTag(NBT_TAME, true);
	}

}
