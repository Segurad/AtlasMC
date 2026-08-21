package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.AbstractVillager;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;

public class CoreAbstractVillager extends CoreMerchant implements AbstractVillager {

	protected static final MetaDataField<VillagerData>
	META_VILLAGER_DATA = new MetaDataField<>(CoreAbstractVillager.LAST_META_INDEX+2, new VillagerData(), EntityMetaTypes.VILLAGER_DATA);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
		
	private int xp;
	
	public CoreAbstractVillager(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_VILLAGER_DATA, new VillagerData());
	}
	
	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public void addXp(int xp) {
		this.xp += xp;
	}
	
	@Override
	public int getXp() {
		return xp;
	}

	@Override
	public VillagerData getVillagerDataUnsafe() {
		return metaContainer.getData(META_VILLAGER_DATA);
	}

	@Override
	public VillagerData getVillagerData() {
		return metaContainer.getData(META_VILLAGER_DATA).clone();
	}

	@Override
	public void setVillagerData(VillagerData data) {
		metaContainer.setData(META_VILLAGER_DATA, data);
	}

}
