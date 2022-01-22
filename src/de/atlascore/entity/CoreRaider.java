package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Raider;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreRaider extends CoreMob implements Raider {

	protected static final MetaDataField<Boolean>
	META_IS_CELEBRATING = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreRaider(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
		metaContainer.get(META_IS_CELEBRATING).setData(celebrating);
	}

}
