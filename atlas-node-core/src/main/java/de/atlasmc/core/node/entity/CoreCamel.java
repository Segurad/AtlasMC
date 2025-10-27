package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.Camel;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.inventory.AbstractHorseInventory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreCamel extends CoreAbstractHorse implements Camel {

	protected static final MetaDataField<Boolean> META_DASHING = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Long> META_LAST_POSE_TICK = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+2, 0L, MetaDataType.VAR_LONG);
	
	protected static final int LAST_META_INDEX = CoreAbstractHorse.LAST_META_INDEX+2;
	
	public CoreCamel(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DASHING);
		metaContainer.set(META_LAST_POSE_TICK);
	}

	@Override
	public long getLastPoseTick() {
		return metaContainer.getData(META_LAST_POSE_TICK);
	}

	@Override
	public void setLastPoseTick(long pose) {
		metaContainer.get(META_LAST_POSE_TICK).setData(pose);
	}

	@Override
	public boolean isDashing() {
		return metaContainer.getData(META_DASHING);
	}

	@Override
	public void setDashing(boolean dashing) {
		metaContainer.get(META_DASHING).setData(dashing);
	}

	@Override
	protected AbstractHorseInventory createInventory() {
		return (AbstractHorseInventory) InventoryType.HORSE.create(this);
	}

}
