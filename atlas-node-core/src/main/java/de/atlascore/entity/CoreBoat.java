package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Boat;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

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
	
	protected static final NBTFieldContainer<CoreBoat> NBT_FIELDS;
	
	protected static final CharKey
	NBT_TYPE = CharKey.literal("Type");
	
	static {
		NBT_FIELDS = CoreVehicle.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_TYPE, (holder, reader) -> {
			holder.setBoatType(BoatType.getByNameID(reader.readStringTag()));
		});
	}
	
	public CoreBoat(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBoat> getFieldContainerRoot() {
		return NBT_FIELDS;
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

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(NBT_TYPE, getBoatType().getNameID());
	}
	
}
