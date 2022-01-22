package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Strider;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreStrider extends CoreAgeableMob implements Strider {

	protected static final MetaDataField<Integer>
	META_BOOST_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_IS_SHAKING = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_HAS_SADDLE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+3;
	
	public CoreStrider(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
		metaContainer.get(META_BOOST_TIME).setData(time);		
	}

	@Override
	public void setShaking(boolean shaking) {
		metaContainer.get(META_IS_SHAKING).setData(shaking);		
	}

	@Override
	public void setSaddle(boolean saddle) {
		metaContainer.get(META_HAS_SADDLE).setData(saddle);		
	}

}
