package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Tameable;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTameable extends CoreAgeableMob implements Tameable {
	
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is tamed
	 */
	protected static final MetaDataField<Byte>
	META_TAMEABLE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<UUID>
	META_OWNER = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, null, MetaDataType.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	protected static final NBTFieldContainer<CoreTameable> NBT_FIELDS;
	
	protected static final CharKey
	NBT_OWNER = CharKey.literal("Owner"),
	NBT_SITTING = CharKey.literal("Sitting");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreAgeableMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_OWNER, (holder, reader) -> {
			holder.setOwner(reader.readUUID());
		});
		NBT_FIELDS.setField(NBT_SITTING, (holder, reader) -> {
			holder.setSitting(reader.readByteTag() == 1);
		});
	}
	
	public CoreTameable(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreTameable> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TAMEABLE_FLAGS);
		metaContainer.set(META_OWNER);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isSitting() {
		return (metaContainer.getData(META_TAMEABLE_FLAGS) & 0x01) == 0x01;
	}

	@Override
	public void setSitting(boolean sitting) {
		MetaData<Byte> data = metaContainer.get(META_TAMEABLE_FLAGS);
		data.setData((byte) (sitting ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

	@Override
	public boolean isTamed() {
		return (metaContainer.getData(META_TAMEABLE_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public void setTamed(boolean tamed) {
		MetaData<Byte> data = metaContainer.get(META_TAMEABLE_FLAGS);
		data.setData((byte) (tamed ? data.getData() | 0x04 : data.getData() & 0xFB));
	}

	@Override
	public UUID getOwner() {
		return metaContainer.getData(META_OWNER);
	}

	@Override
	public void setOwner(UUID owner) {
		metaContainer.get(META_OWNER).setData(owner);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getOwner() != null)
			writer.writeUUID(NBT_OWNER, getOwner());
		if (isSitting())
			writer.writeByteTag(NBT_SITTING, true);
	}

}
