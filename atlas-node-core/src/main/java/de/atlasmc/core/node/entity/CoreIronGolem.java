package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.IronGolem;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreIronGolem extends CoreMob implements IronGolem {

	protected static final MetaDataField<Byte>
	META_IRON_GOLEM_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreIronGolem(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IRON_GOLEM_FLAGS);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isPlayerCreated() {
		return (metaContainer.getData(META_IRON_GOLEM_FLAGS) & 0x01) == 0x01;
	}

	@Override
	public void setPlayerCreated(boolean playercreated) {
		MetaData<Byte> data = metaContainer.get(META_IRON_GOLEM_FLAGS);
		data.setData((byte) (playercreated ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

}
