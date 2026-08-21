package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.Blaze;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;

public class CoreBlaze extends CoreMob implements Blaze {
	
	protected static final MetaDataField<Byte>
	META_BLAZE_ON_FIRE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, EntityMetaTypes.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreBlaze(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_BLAZE_ON_FIRE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isOnFire() {
		return (metaContainer.getData(META_BLAZE_ON_FIRE) & 0x01) == 0x01;
	}

	@Override
	public void setOnFire(boolean fire) {
		MetaData<Byte> data = metaContainer.get(META_BLAZE_ON_FIRE);
		metaContainer.setData(META_BLAZE_ON_FIRE, (byte) (fire ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

}
