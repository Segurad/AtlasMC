package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.AbstractPiglin;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;

public class CoreAbstractPiglin extends CoreMob implements AbstractPiglin {

	protected static final MetaDataField<Boolean>
	META_IMMUNE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, EntityMetaTypes.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreAbstractPiglin(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IMMUNE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isImmune() {
		return metaContainer.getData(META_IMMUNE);
	}

	@Override
	public void setImmune(boolean immune) {
		metaContainer.setData(META_IMMUNE, immune);		
	}
	
}
