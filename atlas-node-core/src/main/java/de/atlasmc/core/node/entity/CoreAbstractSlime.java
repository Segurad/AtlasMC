package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.AbstractSlime;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;

public class CoreAbstractSlime extends CoreMob implements AbstractSlime {

	protected static final MetaDataField<Integer>
	META_SLIME_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 1, EntityMetaTypes.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreAbstractSlime(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SLIME_SIZE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getSize() {
		return metaContainer.getData(META_SLIME_SIZE);
	}

	@Override
	public void setSize(int size) {
		metaContainer.setData(META_SLIME_SIZE, size);		
	}
	
}
