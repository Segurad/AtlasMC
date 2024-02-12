package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Goat;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreGoat extends CoreAgeableMob implements Goat {

	protected static final MetaDataField<Boolean> META_IS_SCREAMING_GOAT = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_HAS_LEFT_HORN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, true, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_HAS_RIGHT_HORN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, true, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+3;
	
	protected static final NBTFieldContainer<CoreGoat> NBT_FIELDS;
	
	private static final CharKey
	NBT_HAS_LEFT_HORN = CharKey.literal("HasLeftHorn"),
	NBT_HAS_RIGHT_HORN = CharKey.literal("HasRightHorn"),
	NBT_IS_SCREAMING_GOAT = CharKey.literal("IsScreamingGoat");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreAgeableMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_HAS_LEFT_HORN, (holder, reader) -> {
			holder.setLeftHorn(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_HAS_RIGHT_HORN, (holder, reader) -> {
			holder.setRightHorn(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_IS_SCREAMING_GOAT, (holder, reader) -> {
			holder.setScreamingGoat(reader.readByteTag() == 1);
		});
	}
	
	public CoreGoat(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_SCREAMING_GOAT);
		metaContainer.set(META_HAS_LEFT_HORN);
		metaContainer.set(META_HAS_RIGHT_HORN);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreGoat> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public boolean isScreamingGoat() {
		return metaContainer.getData(META_IS_SCREAMING_GOAT);
	}

	@Override
	public void setScreamingGoat(boolean screaming) {
		metaContainer.get(META_IS_SCREAMING_GOAT).setData(screaming);
	}

	@Override
	public boolean hasLeftHorn() {
		return metaContainer.getData(META_HAS_LEFT_HORN);
	}

	@Override
	public void setLeftHorn(boolean horn) {
		metaContainer.get(META_HAS_LEFT_HORN).setData(horn);
	}

	@Override
	public boolean hasRightHorn() {
		return metaContainer.getData(META_HAS_RIGHT_HORN);
	}

	@Override
	public void setRightHorn(boolean horn) {
		metaContainer.get(META_HAS_RIGHT_HORN).setData(horn);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!hasLeftHorn())
			writer.writeByteTag(NBT_HAS_LEFT_HORN, false);
		if (!hasRightHorn())
			writer.writeByteTag(NBT_HAS_RIGHT_HORN, false);
		if (isScreamingGoat())
			writer.writeByteTag(NBT_IS_SCREAMING_GOAT, true);
	}

}
