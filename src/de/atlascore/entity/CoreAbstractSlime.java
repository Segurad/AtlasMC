package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.AbstractSlime;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAbstractSlime extends CoreMob implements AbstractSlime {

	protected static final MetaDataField<Integer>
	META_SLIME_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 1, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_SIZE = CharKey.of("Size");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_SIZE, (holder, reader) -> {
			if (holder instanceof AbstractSlime) {
				((AbstractSlime) holder).setSize(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	public CoreAbstractSlime(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
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
