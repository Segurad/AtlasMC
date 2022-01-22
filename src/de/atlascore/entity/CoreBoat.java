package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Boat;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreBoat extends CoreVehicle implements Boat {

	protected static final MetaDataField<Integer>
	META_TIME_SINCE_LAST_HIT = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_FORWARD_DIRECTION = new MetaDataField<Integer>(CoreVehicle.LAST_META_INDEX+2, 1, MetaDataType.INT);
	protected static final MetaDataField<Float>
	META_DAMAGE_TAKEN = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+3, 0.0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_BOAT_TYPE = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+4, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_LEFT_PADDLE_TURNING = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+5, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_RIGHT_PADDLE_TURNING = new MetaDataField<Boolean>(CoreVehicle.LAST_META_INDEX+6, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_SPLASH_TIMER = new MetaDataField<>(CoreVehicle.LAST_META_INDEX+7, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreVehicle.LAST_META_INDEX+7;
	
	public CoreBoat(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TIME_SINCE_LAST_HIT);
		metaContainer.set(META_FORWARD_DIRECTION);
		metaContainer.set(META_DAMAGE_TAKEN);
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
	public int getTimeSinceLastHit() {
		return metaContainer.getData(META_TIME_SINCE_LAST_HIT);
	}

	@Override
	public int getForwardDirection() {
		return metaContainer.getData(META_FORWARD_DIRECTION);
	}

	@Override
	public float getDamageTaken() {
		return metaContainer.getData(META_DAMAGE_TAKEN);
	}

	@Override
	public BoatType getBoatType() {
		return BoatType.getByID(metaContainer.getData(META_BOAT_TYPE));
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
	public void setDamageTaken(float damage) {
		metaContainer.get(META_DAMAGE_TAKEN).setData(damage);
	}

	@Override
	public void setBoatType(BoatType type) {
		metaContainer.get(META_BOAT_TYPE).setData(type.getID());
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
