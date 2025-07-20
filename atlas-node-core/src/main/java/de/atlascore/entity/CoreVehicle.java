package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vehicle;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreVehicle extends CoreEntity implements Vehicle {
	
	protected static final MetaDataField<Integer>
	META_SHAKING_POWER = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer> // TODO research direction values
	META_SHAKING_DIRECTION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 1, MetaDataType.VAR_INT);
	protected static final MetaDataField<Float>
	META_SHAKING_MULTIPLIER = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, 0.0f, MetaDataType.FLOAT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+3;

	public CoreVehicle(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SHAKING_POWER);
		metaContainer.set(META_SHAKING_DIRECTION);
		metaContainer.set(META_SHAKING_MULTIPLIER);
	}
	
	@Override
	public int getShakingPower() {
		return metaContainer.getData(META_SHAKING_POWER);
	}

	@Override
	public void setShakingPower(int power) {
		if (power < 0)
			throw new IllegalArgumentException("Power can not be lower than 0: " + power);
		metaContainer.get(META_SHAKING_POWER).setData(power);		
	}

	@Override
	public int getShakingDirection() {
		return metaContainer.getData(META_SHAKING_DIRECTION);
	}

	@Override
	public void setShakingDirection(int direction) {
		metaContainer.get(META_SHAKING_DIRECTION).setData(direction);
	}

	@Override
	public float getShakingMultiplier() {
		return metaContainer.getData(META_SHAKING_MULTIPLIER);
	}

	@Override
	public void setShakingMultiplier(float multiplier) {
		metaContainer.get(META_SHAKING_MULTIPLIER).setData(multiplier);		
	}

}
