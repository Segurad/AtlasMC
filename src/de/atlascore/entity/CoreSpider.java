package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Spider;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreSpider extends CoreMob implements Spider {

	protected static final MetaDataField<Byte>
	META_SPIDER_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreSpider(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
