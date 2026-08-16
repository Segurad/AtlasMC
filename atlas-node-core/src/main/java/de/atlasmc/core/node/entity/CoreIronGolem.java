package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.IronGolem;
import de.atlasmc.node.entity.metadata.MetaData;
import de.atlasmc.node.entity.metadata.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreIronGolem extends CoreMob implements IronGolem {
	
	protected static final int
	FLAG_IS_PLAYER_CREATED = 0x01;
	
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
	
	protected void setIronGolemFlag(int flag, boolean set) {
		MetaData<Byte> data = metaContainer.get(META_IRON_GOLEM_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		metaContainer.setData(META_IRON_GOLEM_FLAGS, value);
	}

	@Override
	public void setPlayerCreated(boolean playercreated) {
		setIronGolemFlag(FLAG_IS_PLAYER_CREATED, playercreated);
	}

}
