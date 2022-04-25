package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PrimedTNT;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CorePrimedTNT extends CoreEntity implements PrimedTNT {

	protected static final MetaDataField<Integer>
	META_FUSE_TIME = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 80, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_FUSE = "Fuse";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_FUSE, (holder, reader) -> {
			((PrimedTNT) holder).setFuseTime(reader.readIntTag());
		});
	}
	
	public CorePrimedTNT(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
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
		writer.writeIntTag(NBT_FUSE, getFuseTime());
	}

}
