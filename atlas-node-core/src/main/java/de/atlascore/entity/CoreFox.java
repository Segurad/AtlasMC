package de.atlascore.entity;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Fox;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFox extends CoreAgeableMob implements Fox {

	protected static final MetaDataField<Integer>
	META_FOX_TYPE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is crouching<br>
	 * 0x08 - Is interested<br>
	 * 0x10 - Is pouncing<br>
	 * 0x20 - Is sleeping<br>
	 * 0x40 - Is faceplanted<br>
	 * 0x80 - Is defending<br>
	 */
	protected static final MetaDataField<Byte>
	META_FOX_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<UUID>
	META_FOX_FIRST_TRUSTED = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, null, MetaDataType.OPT_UUID);
	protected static final MetaDataField<UUID>
	META_FOX_LAST_TRUSTED = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX + 4, null, MetaDataType.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+4;
	
	protected static final NBTFieldContainer<CoreFox> NBT_FIELDS;
	
	protected static final CharKey
	NBT_TRUSTED = CharKey.literal("Trusted"),
	NBT_TYPE = CharKey.literal("Type"),
	NBT_SLEEPING = CharKey.literal("Sleeping"),
	NBT_SITTING = CharKey.literal("Sitting"),
	NBT_CROUCHING = CharKey.literal("Crouching");
	
	static {
		NBT_FIELDS = CoreAgeableMob.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_TRUSTED, (holder, reader) -> {
			while (reader.getRestPayload() > 0) {
				holder.addTrusted(reader.readUUID());
			}
		});
		NBT_FIELDS.setField(NBT_TYPE, (holder, reader) -> {
			holder.setFoxType(Type.getByNameID(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_SLEEPING, (holder, reader) -> {
			holder.setSleeping(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SITTING, (holder, reader) -> {
			holder.setSitting(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_CROUCHING, (holder, reader) -> {
			holder.setCrouching(reader.readByteTag() == 1);
		});
	}
	
	private Set<UUID> trusted;
	
	public CoreFox(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FOX_TYPE);
		metaContainer.set(META_FOX_FLAGS);
		metaContainer.set(META_FOX_FIRST_TRUSTED);
		metaContainer.set(META_FOX_LAST_TRUSTED);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreFox> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public Type getFoxType() {
		return Type.getByID(metaContainer.getData(META_FOX_TYPE));
	}

	@Override
	public boolean isSitting() {
		return (metaContainer.getData(META_FOX_FLAGS) & 0x01) == 0x01;
	}

	@Override
	public boolean isInterested() {
		return (metaContainer.getData(META_FOX_FLAGS) & 0x08) == 0x08;
	}

	@Override
	public boolean isPouncing() {
		return (metaContainer.getData(META_FOX_FLAGS) & 0x10) == 0x10;
	}

	@Override
	public boolean isSleeping() {
		return (metaContainer.getData(META_FOX_FLAGS) & 0x20) == 0x20;
	}

	@Override
	public boolean isFaceplanted() {
		return (metaContainer.getData(META_FOX_FLAGS) & 0x40) == 0x40;
	}

	@Override
	public boolean isDefending() {
		return (metaContainer.getData(META_FOX_FLAGS) & 0x80) == 0x80;
	}

	@Override
	public UUID getFirstTrusted() {
		return metaContainer.getData(META_FOX_FIRST_TRUSTED);
	}

	@Override
	public UUID getSecondTrusted() {
		return metaContainer.getData(META_FOX_LAST_TRUSTED);
	}

	@Override
	public void setFoxType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_FOX_TYPE).setData(type.getID());
	}

	@Override
	public void setSitting(boolean sitting) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (sitting ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

	@Override
	public void setInterested(boolean interested) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (interested ? data.getData() | 0x08 : data.getData() & 0xF7));
	}

	@Override
	public void setPouncing(boolean pouncing) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (pouncing ? data.getData() | 0x10 : data.getData() & 0xEF));
	}

	@Override
	public void setSleeping(boolean sleeping) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (sleeping ? data.getData() | 0x20 : data.getData() & 0xDF));
	}

	@Override
	public void setFaceplanted(boolean faceplanted) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (faceplanted ? data.getData() | 0x40 : data.getData() & 0xBF));
	}

	@Override
	public void setDefending(boolean defending) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (defending ? data.getData() | 0x80 : data.getData() & 0x7F));		
	}

	@Override
	public void setFirstTrusted(UUID uuid) {
		metaContainer.get(META_FOX_FIRST_TRUSTED).setData(uuid);		
	}

	@Override
	public void setSecondTrusted(UUID uuid) {
		metaContainer.get(META_FOX_LAST_TRUSTED).setData(uuid);		
	}

	@Override
	public void setCrouching(boolean crouching) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (crouching ? data.getData() | 0x04 : data.getData() & 0xFB));
	}
	
	@Override
	public boolean isCrouching() {
		return (metaContainer.getData(META_FOX_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public void addTrusted(UUID trusted) {
		if (trusted == null)
			throw new IllegalArgumentException("Trusted can not be null!");
		getTrusted().add(trusted);
	}

	@Override
	public boolean isTrusted(UUID trusted) {
		if (trusted == null || !hasTrusted())
			return false;
		return this.trusted.contains(trusted);
	}

	@Override
	public Set<UUID> getTrusted() {
		if (trusted == null)
			trusted = new HashSet<>();
		return trusted;
	}

	@Override
	public boolean removeTrusted(UUID trusted) {
		if (trusted == null || !hasTrusted())
			return false;
		return this.trusted.remove(trusted);
	}

	@Override
	public boolean hasTrusted() {
		return trusted != null && !trusted.isEmpty();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasTrusted()) {
			Set<UUID> trusted = getTrusted();
			writer.writeListTag(NBT_TRUSTED, TagType.INT_ARRAY, trusted.size());
			for (UUID uuid : trusted)
				writer.writeUUID(null, uuid);
		}
		writer.writeStringTag(NBT_TYPE, getFoxType().getNameID());
		if (isSleeping())
			writer.writeByteTag(NBT_SLEEPING, true);
		if (isSitting())
			writer.writeByteTag(NBT_SITTING, true);
		if (isCrouching())
			writer.writeByteTag(NBT_CROUCHING, true);
	}

}
