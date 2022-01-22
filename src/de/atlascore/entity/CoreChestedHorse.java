package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.ChestedHorse;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreChestedHorse extends CoreAbstractHorse implements ChestedHorse {
	
	protected static final MetaDataField<Boolean>
	META_HORSE_HAS_CHEST = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractHorse.LAST_META_INDEX+1;

	public CoreChestedHorse(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HORSE_HAS_CHEST);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasChest() {
		return metaContainer.getData(META_HORSE_HAS_CHEST);
	}

	@Override
	public void setChest(boolean chest) {
		metaContainer.get(META_HORSE_HAS_CHEST).setData(chest);		
	}

}
