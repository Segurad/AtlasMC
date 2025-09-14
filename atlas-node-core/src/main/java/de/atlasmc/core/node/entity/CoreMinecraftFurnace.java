package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.MinecartFurnace;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreMinecraftFurnace extends CoreAbstractMinecart implements MinecartFurnace {

	protected static final MetaDataField<Boolean>
	META_HAS_FUEL = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractMinecart.LAST_META_INDEX+1;
	
	private int fuelLevel = -1;
	private double pushX;
	private double pushZ;
	
	public CoreMinecraftFurnace(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HAS_FUEL);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasFuel() {
		return metaContainer.getData(META_HAS_FUEL);
	}

	@Override
	public void setFuel(boolean fuel) {
		metaContainer.get(META_HAS_FUEL).setData(fuel);		
	}

	@Override
	public void setFuelLevel(int ticks) {
		this.fuelLevel = ticks;
	}

	@Override
	public int getFuelLevel() {
		return fuelLevel;
	}
	
	@Override
	public double getPushX() {
		return pushX;
	}
	
	@Override
	public void setPushX(double x) {
		this.pushX = x;
	}
	
	@Override
	public double getPushZ() {
		return pushZ;
	}
	
	@Override
	public void setPushZ(double z) {
		this.pushZ = z;
	}

}
