package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ZombieVillager;
import de.atlasmc.entity.Villager.VillagerData;
import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlasmc.entity.Villager.VillagerType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreZombieVillager extends CoreZombie implements ZombieVillager {

	protected static final MetaDataField<Boolean>
	META_IS_CONVERTING = new MetaDataField<>(CoreZombie.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<VillagerData>
	META_VILLAGER_DATA = new MetaDataField<VillagerData>(CoreZombie.LAST_META_INDEX+2, null, MetaDataType.VILLAGER_DATA);
	
	protected static final int LAST_META_INDEX = CoreZombie.LAST_META_INDEX+2;
	
	public CoreZombieVillager(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_CONVERTING);
		metaContainer.set(META_VILLAGER_DATA);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isConverting() {
		return metaContainer.getData(META_IS_CONVERTING);
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

	@Override
	public void setConverting(boolean converting) {
		metaContainer.get(META_IS_CONVERTING).setData(converting);
	}

}
