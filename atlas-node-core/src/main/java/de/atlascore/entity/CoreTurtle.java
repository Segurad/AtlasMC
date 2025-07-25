package de.atlascore.entity;

import org.joml.Vector3i;

import de.atlasmc.Location;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Turtle;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.MathUtil;

public class CoreTurtle extends CoreAgeableMob implements Turtle {

	protected static final MetaDataField<Long>
	META_HOME_POS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0L, MetaDataType.POSITION);
	protected static final MetaDataField<Boolean>
	META_HAS_EGG = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_LAYING_EGG = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Long>
	META_TRAVEL_POS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+4, 0L, MetaDataType.POSITION);
	protected static final MetaDataField<Boolean>
	META_IS_GOING_HOME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+5, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_TRAVELING = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+6, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+6;
	
	public CoreTurtle(EntityType type) {
		super(type);
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
	public boolean isLayingEgg() {
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
	public void setHomePosition(Vector3i pos) {
		super.setHomePosition(pos);
		metaContainer.get(META_HOME_POS).setData(MathUtil.toPosition(pos));
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
