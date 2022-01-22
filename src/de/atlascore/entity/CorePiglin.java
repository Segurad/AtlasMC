package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Piglin;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CorePiglin extends CoreAbstractPiglin implements Piglin {
	
	protected static final MetaDataField<Boolean>
	META_IS_BABY = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_CHARGING_CROSSBOW = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_DANCING = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);

	protected static final int LAST_META_INDEX = CoreAbstractPiglin.LAST_META_INDEX+3;
	
	public CorePiglin(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BABY);
		metaContainer.set(META_IS_CHARGING_CROSSBOW);
		metaContainer.set(META_IS_DANCING);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isBaby() {
		return metaContainer.getData(META_IS_BABY);
	}

	@Override
	public boolean isChargingCrossbow() {
		return metaContainer.getData(META_IS_CHARGING_CROSSBOW);
	}

	@Override
	public boolean isDancing() {
		return metaContainer.getData(META_IS_SILENT);
	}

	@Override
	public void setBaby(boolean baby) {
		metaContainer.get(META_IS_BABY).setData(baby);		
	}

	@Override
	public void setChargingCorssbow(boolean charging) {
		metaContainer.get(META_IS_CHARGING_CROSSBOW).setData(charging);		
	}

	@Override
	public void setDancing(boolean dancing) {
		metaContainer.get(META_IS_DANCING).setData(dancing);		
	}

}
