package de.atlascore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ZombieVillager;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.MerchantInventory;

public class CoreZombieVillager extends CoreZombie implements ZombieVillager {

	protected static final MetaDataField<Boolean>
	META_IS_CONVERTING = new MetaDataField<>(CoreZombie.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<VillagerData>
	META_VILLAGER_DATA = new MetaDataField<>(CoreZombie.LAST_META_INDEX + 2, null, MetaDataType.VILLAGER_DATA);
	
	protected static final int LAST_META_INDEX = CoreZombie.LAST_META_INDEX+2;
	
	private MerchantInventory inv;
	private List<MerchantRecipe> recipes;
	private int xp;
	private UUID conversionPlayer;
	private int conversionTime = -1;
	
	public CoreZombieVillager(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_CONVERTING);
		metaContainer.set(META_VILLAGER_DATA, new VillagerData());
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
	public void setConverting(boolean converting) {
		metaContainer.get(META_IS_CONVERTING).setData(converting);
	}

	@Override
	public MerchantRecipe getRecipe(int index) {
		if (index >= getRecipeCount() || index < 0)
			return null;
		return recipes.get(index);
	}

	@Override
	public int getRecipeCount() {
		return recipes == null ? 0 : recipes.size();
	}

	@Override
	public List<MerchantRecipe> getRecipes() {
		if (recipes == null)
			recipes = new ArrayList<>();
		return recipes;
	}

	@Override
	public void addRecipe(MerchantRecipe recipe) {
		getRecipes().add(recipe);
	}

	@Override
	public boolean hasRecipes() {
		return recipes != null && !recipes.isEmpty();
	}

	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public int getXp() {
		return xp;
	}

	@Override
	public void setConversionPlayer(UUID uuid) {
		this.conversionPlayer = uuid;
	}

	@Override
	public UUID getConversionPlayer() {
		return conversionPlayer;
	}

	@Override
	public void setConversionTime(int ticks) {
		this.conversionTime = ticks;
	}

	@Override
	public int getConversionTime() {
		return conversionTime;
	}

	@Override
	public int getHeadShakeTimer() {
		return 0;
	}

	@Override
	public void setHeadShakeTimer(int time) {}

	@Override
	public void addXp(int xp) {
		this.xp += xp;
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

	@Override
	public MerchantInventory getInventory() {
		if (inv == null)
			inv = ContainerFactory.MERCHANT_INV_FACTORY.create(InventoryType.MERCHANT, this);
		return inv;
	}

	@Override
	public boolean hasInventory() {
		return inv != null;
	}

}
