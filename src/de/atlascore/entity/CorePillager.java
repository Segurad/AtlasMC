package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Pillager;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CorePillager extends CoreRaider implements Pillager {

	protected static final MetaDataField<Boolean>
	META_IS_CHARGING = new MetaDataField<>(CoreRaider.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreRaider.LAST_META_INDEX+1;
	
	public CorePillager(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_CHARGING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isCharging() {
		return metaContainer.getData(META_IS_CHARGING);
	}

	@Override
	public void setCharging(boolean charging) {
		metaContainer.get(META_IS_CELEBRATING).setData(charging);
	}

}
