package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Strider;

public class CoreStrider extends CoreAgeableMob implements Strider {

	protected static final MetaDataField<Integer>
	META_BOOST_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_IS_SHAKING = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, false, EntityMetaTypes.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_HAS_SADDLE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, false, EntityMetaTypes.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+3;
	
	public CoreStrider(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_BOOST_TIME);
		metaContainer.set(META_IS_SHAKING);
		metaContainer.set(META_HAS_SADDLE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public int getBoostTime() {
		return metaContainer.getData(META_BOOST_TIME);
	}

	@Override
	public boolean isShaking() {
		return metaContainer.getData(META_IS_SHAKING);
	}

	@Override
	public boolean hasSaddle() {
		return metaContainer.getData(META_HAS_SADDLE);
	}

	@Override
	public void setBoostTime(int time) {
		metaContainer.setData(META_BOOST_TIME, time);		
	}

	@Override
	public void setShaking(boolean shaking) {
		metaContainer.setData(META_IS_SHAKING, shaking);		
	}

	@Override
	public void setSaddle(boolean saddle) {
		metaContainer.setData(META_HAS_SADDLE, saddle);		
	}
	
}
