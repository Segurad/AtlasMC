package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Painting;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CorePainting extends CoreHanging implements Painting {

	protected static final MetaDataField<Motive> META_MOTIVE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, Motive.KEBAB, MetaDataType.PAINTING_VARIANT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	public CorePainting(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_MOTIVE);
	}

	@Override
	public Motive getMotive() {
		return metaContainer.getData(META_MOTIVE);
	}

	@Override
	public void setMotive(Motive motive) {
		metaContainer.get(META_MOTIVE).setData(motive);
	}

}
