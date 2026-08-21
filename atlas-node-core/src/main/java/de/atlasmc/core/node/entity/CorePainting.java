package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Painting;

public class CorePainting extends CoreHanging implements Painting {

	protected static final MetaDataField<Motive> META_MOTIVE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, Motive.KEBAB, EntityMetaTypes.PAINTING_VARIANT);
	
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
		metaContainer.setData(META_MOTIVE, motive);
	}

}
