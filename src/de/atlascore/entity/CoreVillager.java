package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Villager;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreVillager extends CoreAbstractVillager implements Villager {
	
	protected static final MetaDataField<VillagerData>
	META_VILLAGER_DATA = new MetaDataField<>(CoreAbstractVillager.LAST_META_INDEX+1, null, MetaDataType.VILLAGER_DATA);
	
	protected static final int LAST_META_INDEX = CoreAbstractVillager.LAST_META_INDEX+1;
	
	public CoreVillager(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public VillagerType getVillagerType() {
		return metaContainer.getData(META_VILLAGER_DATA).getType();
	}

	@Override
	public void setVillagerType(VillagerType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		MetaData<VillagerData> data = metaContainer.get(META_VILLAGER_DATA);
		data.getData().setType(type);
		data.setChanged(true);
	}

	@Override
	public VillagerProfession getVillagerProfession() {
		return metaContainer.getData(META_VILLAGER_DATA).getProfession();
	}

	@Override
	public void setVillagerProfession(VillagerProfession profession) {
		if (profession == null)
			throw new IllegalArgumentException("Profession can not be null!");
		MetaData<VillagerData> data = metaContainer.get(META_VILLAGER_DATA);
		data.getData().setProfession(profession);
		data.setChanged(true);
	}

	@Override
	public int getLevel() {
		return metaContainer.getData(META_VILLAGER_DATA).getLevel();
	}

	@Override
	public void setLevel(int level) {
		if (level < 1)
			throw new IllegalArgumentException("Level can not be lower than 1: " + level);
		MetaData<VillagerData> data = metaContainer.get(META_VILLAGER_DATA);
		data.getData().setLevel(level);
		data.setChanged(true);
	}

}
