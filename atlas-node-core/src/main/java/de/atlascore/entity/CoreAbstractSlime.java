package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.AbstractSlime;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAbstractSlime extends CoreMob implements AbstractSlime {

	protected static final MetaDataField<Integer>
	META_SLIME_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 1, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreAbstractSlime> NBT_FIELDS;
	
	protected static final CharKey
	NBT_SIZE = CharKey.literal("Size");
	
	static {
		NBT_FIELDS = CoreMob.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_SIZE, (holder, reader) -> {
			holder.setSize(reader.readIntTag());
		});
	}
	
	public CoreAbstractSlime(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAbstractSlime> getFieldSetRoot() {
		return NBT_FIELDS;
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SLIME_SIZE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getSize() {
		return metaContainer.getData(META_SLIME_SIZE);
	}

	@Override
	public void setSize(int size) {
		metaContainer.get(META_SLIME_SIZE).setData(size);		
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_SIZE, getSize());
	}
	
}
