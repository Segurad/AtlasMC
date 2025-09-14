package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.SnowGolem;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreSnowGolem extends CoreMob implements SnowGolem {

	protected static final int
	FLAG_HAS_HAT = 0x10;
	
	protected static final MetaDataField<Byte>
	META_SNOW_GOLEM_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0x10, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreSnowGolem(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SNOW_GOLEM_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasPumpkinHat() {
		return (metaContainer.getData(META_SNOW_GOLEM_FLAGS) & FLAG_HAS_HAT) == FLAG_HAS_HAT;
	}

	@Override
	public void setPumkinHat(boolean hat) {
		MetaData<Byte> data = metaContainer.get(META_SNOW_GOLEM_FLAGS);
		data.setData((byte) (hat ? data.getData() | FLAG_HAS_HAT : data.getData() & ~FLAG_HAS_HAT));
	}
	
}
