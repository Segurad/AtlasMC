package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PrimedTNT;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePrimedTNT extends CoreEntity implements PrimedTNT {

	protected static final MetaDataField<Integer>
	META_FUSE_TIME = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 80, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer<CorePrimedTNT> NBT_FIELDS;
	
	protected static final CharKey
	NBT_FUSE = CharKey.literal("Fuse");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_FUSE, (holder, reader) -> {
			holder.setFuseTime(reader.readShortTag());
		});
	}
	
	private Entity source;
	
	public CorePrimedTNT(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CorePrimedTNT> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FUSE_TIME);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getFuseTime() {
		return metaContainer.getData(META_FUSE_TIME);
	}

	@Override
	public void setFuseTime(int time) {
		if (time < 0)
			throw new IllegalArgumentException("Time can not be lower than 0: " + time);
		metaContainer.get(META_FUSE_TIME).setData(time);		
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeShortTag(NBT_FUSE, getFuseTime());
	}

	@Override
	public Entity getSource() {
		return source;
	}

	@Override
	public void setSource(Entity source) {
		this.source = source;
	}

}
