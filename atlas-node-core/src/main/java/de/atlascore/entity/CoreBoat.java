package de.atlascore.entity;

import de.atlasmc.entity.Boat;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreBoat extends CoreVehicle implements Boat {

	protected static final MetaDataField<Integer>
	META_BOAT_TYPE = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_LEFT_PADDLE_TURNING = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_RIGHT_PADDLE_TURNING = new MetaDataField<>(CoreVehicle.LAST_META_INDEX + 3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_SPLASH_TIMER = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+4, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreVehicle.LAST_META_INDEX+4;
	
	public CoreBoat(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_BOAT_TYPE);
		metaContainer.set(META_LEFT_PADDLE_TURNING);
		metaContainer.set(META_RIGHT_PADDLE_TURNING);
		metaContainer.set(META_SPLASH_TIMER);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isLeftPaddleTurning() {
		return metaContainer.getData(META_LEFT_PADDLE_TURNING);
	}

	@Override
	public boolean isRightPaddleTurning() {
		return metaContainer.getData(META_RIGHT_PADDLE_TURNING);
	}

	@Override
	public int getSplashTimer() {
		return metaContainer.getData(META_SPLASH_TIMER);
	}
	
	@Override
	public void setLeftPaddleTurning(boolean turning) {
		metaContainer.get(META_LEFT_PADDLE_TURNING).setData(turning);
	}

	@Override
	public void setRightPaddleTurning(boolean turning) {
		metaContainer.get(META_RIGHT_PADDLE_TURNING).setData(turning);
	}

}
