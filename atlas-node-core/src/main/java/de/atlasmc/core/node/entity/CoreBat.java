package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.Bat;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreBat extends CoreMob implements Bat {

	protected static final int FLAG_IS_HANGING = 0x01;
	
	/**
	 * 0x01 Is hanging
	 */
	protected static final MetaDataField<Byte>
	META_BAT_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreBat(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_BAT_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isHanging() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & FLAG_IS_HANGING) == FLAG_IS_HANGING;
	}
	
	@Override
	public void setHanging(boolean hanging) {
		MetaData<Byte> data = metaContainer.get(META_BAT_FLAGS);
		data.setData((byte) (hanging ? data.getData() | FLAG_IS_HANGING : data.getData() & ~FLAG_IS_HANGING));
	}

}
