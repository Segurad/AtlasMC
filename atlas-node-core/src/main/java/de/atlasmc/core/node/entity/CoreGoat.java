package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Goat;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreGoat extends CoreAgeableMob implements Goat {

	protected static final MetaDataField<Boolean> 
	META_IS_SCREAMING_GOAT = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> 
	META_HAS_LEFT_HORN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, true, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> 
	META_HAS_RIGHT_HORN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, true, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+3;
	
	public CoreGoat(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_SCREAMING_GOAT);
		metaContainer.set(META_HAS_LEFT_HORN);
		metaContainer.set(META_HAS_RIGHT_HORN);
	}

	@Override
	public boolean isScreamingGoat() {
		return metaContainer.getData(META_IS_SCREAMING_GOAT);
	}

	@Override
	public void setScreamingGoat(boolean screaming) {
		metaContainer.get(META_IS_SCREAMING_GOAT).setData(screaming);
	}

	@Override
	public boolean hasLeftHorn() {
		return metaContainer.getData(META_HAS_LEFT_HORN);
	}

	@Override
	public void setLeftHorn(boolean horn) {
		metaContainer.get(META_HAS_LEFT_HORN).setData(horn);
	}

	@Override
	public boolean hasRightHorn() {
		return metaContainer.getData(META_HAS_RIGHT_HORN);
	}

	@Override
	public void setRightHorn(boolean horn) {
		metaContainer.get(META_HAS_RIGHT_HORN).setData(horn);
	}

}
