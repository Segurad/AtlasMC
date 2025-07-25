package de.atlascore.entity;

import de.atlasmc.entity.Blaze;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreBlaze extends CoreMob implements Blaze {
	
	protected static final MetaDataField<Byte>
	META_BLAZE_ON_FIRE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
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
	
	public boolean isOnFire() {
		return (metaContainer.getData(META_BLAZE_ON_FIRE) & 0x01) == 0x01;
	}

	@Override
	public void setOnFire(boolean fire) {
		MetaData<Byte> data = metaContainer.get(META_BLAZE_ON_FIRE);
		data.setData((byte) (fire ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

}
