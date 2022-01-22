package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Zombie;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreZombie extends CoreMob implements Zombie {

	protected static final MetaDataField<Boolean>
	META_IS_BABY = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	// META_TYPE (LAST_INDEX+2) is no longer used
	protected static final MetaDataField<Boolean>
	META_IS_BECOMING_DROWNED = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;
	
	public CoreZombie(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BABY);
		metaContainer.set(META_IS_BECOMING_DROWNED);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isBaby() {
		return metaContainer.getData(META_IS_BABY);
	}

	@Override
	public boolean isBecomingDrowned() {
		return metaContainer.getData(META_IS_BECOMING_DROWNED);
	}

	@Override
	public void setBaby(boolean baby) {
		metaContainer.get(META_IS_BABY).setData(baby);
	}

	@Override
	public void setBecomingDorwned(boolean drowned) {
		metaContainer.get(META_IS_BECOMING_DROWNED).setData(drowned);		
	}

}
