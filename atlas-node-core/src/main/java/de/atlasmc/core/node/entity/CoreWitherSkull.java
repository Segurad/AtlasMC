package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.WitherSkull;

public class CoreWitherSkull extends CoreAbstractAcceleratingProjectile implements WitherSkull {

	protected static final MetaDataField<Boolean>
	META_SKULL_CHARGED = new MetaDataField<>(CoreAbstractAcceleratingProjectile.LAST_META_INDEX+1, false, EntityMetaTypes.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractAcceleratingProjectile.LAST_META_INDEX+1;
	
	public CoreWitherSkull(EntityType type) {
		super(type);
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
		metaContainer.setData(META_SKULL_CHARGED, charged);		
	}

}
