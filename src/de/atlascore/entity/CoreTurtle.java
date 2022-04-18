package de.atlascore.entity;

import java.util.UUID;
import de.atlasmc.Location;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Turtle;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.MathUtil;
import de.atlasmc.world.World;

public class CoreTurtle extends CoreAgeableMob implements Turtle {

	protected static final MetaDataField<Long>
	META_HOME_POS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0L, MetaDataType.POSISTION);
	protected static final MetaDataField<Boolean>
	META_HAS_EGG = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_LAYING_EGG = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Long>
	META_TRAVEL_POS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+4, 0L, MetaDataType.POSISTION);
	protected static final MetaDataField<Boolean>
	META_IS_GOING_HOME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+5, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_TRAVELING = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+6, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+6;
	
	protected static final String
//	NBT_HOME_POS_X = "HomePosX", TODO unused 
//	NBT_HOME_POS_Y = "HomePosY",
//	NBT_HOME_POS_Z = "HomePosZ",
//	NBT_TRAVEL_POS_X = "TravelPosX",
//	NBT_TRAVEL_POS_Y = "TravelPosY",
//	NBT_TRAVEL_POS_Z = "TravelPosZ",
	NBT_HAS_EGG = "HasEgg";
	
	static {
		NBT_FIELDS.setField(NBT_HAS_EGG, (holder, reader) -> {
			if (holder instanceof Turtle) {
				((Turtle) holder).setEgg(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreTurtle(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HOME_POS);
		metaContainer.set(META_HAS_EGG);
		metaContainer.set(META_IS_LAYING_EGG);
		metaContainer.set(META_TRAVEL_POS);
		metaContainer.set(META_IS_GOING_HOME);
		metaContainer.set(META_IS_TRAVELING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean hasEgg() {
		return metaContainer.getData(META_HAS_EGG);
	}

	@Override
	public boolean hasLayingEgg() {
		return metaContainer.getData(META_IS_LAYING_EGG);
	}

	@Override
	public boolean isGoingHome() {
		return metaContainer.getData(META_IS_GOING_HOME);
	}

	@Override
	public boolean isTraveling() {
		return metaContainer.getData(META_IS_TRAVELING);
	}

	@Override
	public Location getHome(Location loc) {
		return MathUtil.getLocation(getWorld(), loc, metaContainer.getData(META_HOME_POS));
	}

	@Override
	public void setHome(int x, int y, int z) {
		metaContainer.get(META_HOME_POS).setData(MathUtil.toPosition(x, y, z));
	}

	@Override
	public void setEgg(boolean egg) {
		metaContainer.get(META_HAS_EGG).setData(egg);
	}

	@Override
	public void setLayingEgg(boolean laying) {
		metaContainer.get(META_IS_LAYING_EGG).setData(laying);		
	}

	@Override
	public Location getTravelPos(Location loc) {
		return MathUtil.getLocation(getWorld(), loc, metaContainer.getData(META_TRAVEL_POS));
	}

	@Override
	public void setTravelPos(int x, int y, int z) {
		metaContainer.get(META_TRAVEL_POS).setData(MathUtil.toPosition(x, y, z));
	}

	@Override
	public void setGoingHome(boolean home) {
		metaContainer.get(META_IS_GOING_HOME).setData(home);		
	}

	@Override
	public void setTraveling(boolean traveling) {
		metaContainer.get(META_IS_TRAVELING).setData(traveling);
	}

}
