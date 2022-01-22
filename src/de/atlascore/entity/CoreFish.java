package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Fish;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreFish extends CoreMob implements Fish {

	protected static final MetaDataField<Boolean>
	META_FROM_BUCKET = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreFish(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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

}
