package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.WitherSkull;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreWitherSkull extends CoreAbstractFireball implements WitherSkull {

	protected static final MetaDataField<Boolean>
	META_SKULL_CHARGED = new MetaDataField<>(CoreAbstractFireball.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractFireball.LAST_META_INDEX+1;
	
	public CoreWitherSkull(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SKULL_CHARGED);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isCharged() {
		return metaContainer.getData(META_SKULL_CHARGED);
	}

	@Override
	public void setCharged(boolean charged) {
		metaContainer.get(META_SKULL_CHARGED).setData(charged);		
	}

}
