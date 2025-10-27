package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Fish;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreFish extends CoreMob implements Fish {

	protected static final MetaDataField<Boolean>
	META_FROM_BUCKET = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreFish(EntityType type) {
		super(type);
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
