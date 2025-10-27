package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Spider;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreSpider extends CoreMob implements Spider {

	protected static final MetaDataField<Byte>
	META_SPIDER_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
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
		metaContainer.get(META_SPIDER_FLAGS).setData((byte) (climbing ? 0x01 : 0x00));		
	}

}
