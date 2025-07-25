package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Pig;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CorePig extends CoreAgeableMob implements Pig {

	protected static final MetaDataField<Boolean>
	META_HAS_SADDLE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_BOOST_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	private PigVariant variant;
	
	public CorePig(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HAS_SADDLE);
		metaContainer.set(META_BOOST_TIME);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasSaddle() {
		return metaContainer.getData(META_HAS_SADDLE);
	}

	@Override
	public int getBoostTime() {
		return metaContainer.getData(META_BOOST_TIME);
	}

	@Override
	public void setSaddle(boolean saddle) {
		metaContainer.get(META_HAS_SADDLE).setData(saddle);
	}

	@Override
	public void setBoostTime(int time) {
		metaContainer.get(META_BOOST_TIME).setData(time);		
	}

	@Override
	public PigVariant getVariant() {
		return variant;
	}

	@Override
	public void setVariant(PigVariant variant) {
		this.variant = variant;
	}

}
