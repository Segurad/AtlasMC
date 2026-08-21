package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Raider;

public class CoreRaider extends CoreMob implements Raider {

	protected static final MetaDataField<Boolean>
	META_IS_CELEBRATING = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, EntityMetaTypes.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreRaider(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_CELEBRATING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isCelebrating() {
		return metaContainer.getData(META_IS_CELEBRATING);
	}

	@Override
	public void setCelebrating(boolean celebrating) {
		metaContainer.setData(META_IS_CELEBRATING, celebrating);
	}

}
