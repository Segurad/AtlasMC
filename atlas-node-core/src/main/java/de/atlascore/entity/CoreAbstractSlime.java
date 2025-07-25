package de.atlascore.entity;

import de.atlasmc.entity.AbstractSlime;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreAbstractSlime extends CoreMob implements AbstractSlime {

	protected static final MetaDataField<Integer>
	META_SLIME_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 1, MetaDataType.VAR_INT);
	
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
		metaContainer.get(META_SLIME_SIZE).setData(size);		
	}
	
}
