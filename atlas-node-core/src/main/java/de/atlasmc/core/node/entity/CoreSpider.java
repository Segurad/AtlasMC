package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Spider;

public class CoreSpider extends CoreMob implements Spider {

	protected static final MetaDataField<Byte>
	META_SPIDER_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, EntityMetaTypes.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreSpider(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SPIDER_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isClimbing() {
		return metaContainer.getData(META_SPIDER_FLAGS) == 0x01;
	}

	@Override
	public void setClimbing(boolean climbing) {
		metaContainer.setData(META_SPIDER_FLAGS, (byte) (climbing ? 0x01 : 0x00));		
	}

}
