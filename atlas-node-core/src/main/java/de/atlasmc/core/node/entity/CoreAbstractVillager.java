package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.AbstractVillager;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreAbstractVillager extends CoreMerchant implements AbstractVillager {

	protected static final MetaDataField<VillagerData>
	META_VILLAGER_DATA = new MetaDataField<>(CoreAbstractVillager.LAST_META_INDEX+2, null, MetaDataType.VILLAGER_DATA);
	
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
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		MetaData<VillagerData> field = metaContainer.get(META_VILLAGER_DATA);
		field.getData().set(data);
		field.setChanged(true);
	}

}
