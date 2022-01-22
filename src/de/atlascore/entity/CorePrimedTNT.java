package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PrimedTNT;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CorePrimedTNT extends CoreEntity implements PrimedTNT {

	protected static final MetaDataField<Integer>
	META_FUSE_TIME = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 80, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	public CorePrimedTNT(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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

}
