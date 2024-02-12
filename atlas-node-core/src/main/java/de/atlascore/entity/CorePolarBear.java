package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PolarBear;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CorePolarBear extends CoreAgeableMob implements PolarBear {

	protected static final MetaDataField<Boolean>
	META_IS_STANDING_UP = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CorePolarBear(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_STANDING_UP);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isStandingUp() {
		return metaContainer.getData(META_IS_STANDING_UP);
	}

	@Override
	public void setStandingUp(boolean standing) {
		metaContainer.get(META_IS_STANDING_UP).setData(standing);
	}

}
