package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Fish;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFish extends CoreMob implements Fish {

	protected static final MetaDataField<Boolean>
	META_FROM_BUCKET = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final CharKey
	NBT_FROM_BUCKET = CharKey.literal("FromBucket");
	
	static {
		NBT_FIELDS.setField(NBT_FROM_BUCKET, (holder, reader) -> {
			if (holder instanceof Fish) {
				((Fish) holder).setFromBucket(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreFish(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FROM_BUCKET);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isFromBucket() {
		return metaContainer.getData(META_FROM_BUCKET);
	}

	@Override
	public void setFromBucket(boolean from) {
		metaContainer.get(META_FROM_BUCKET).setData(from);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isFromBucket())
			writer.writeByteTag(NBT_FROM_BUCKET, true);
	}

}
