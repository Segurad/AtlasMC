package de.atlascore.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.entity.AbstractVillager;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.MerchantInventory;
import de.atlasmc.world.World;

public class CoreAbstractVillager extends CoreAgeableMob implements AbstractVillager {

	protected static final MetaDataField<Integer>
	META_HEAD_SHAKE_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	private MerchantInventory inv;
	
	public CoreAbstractVillager(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HEAD_SHAKE_TIME);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public MerchantRecipe getRecipe(int index) {
		return getInventory().getRecipe(index);
	}

	@Override
	public int getRecipeCount() {
		return getInventory().getRecipeCount();
	}

	@Override
	public List<MerchantRecipe> getRecipes() {
		return getInventory().getRecipes();
	}

	@Override
	public int getHeadShakeTimer() {
		return metaContainer.getData(META_HEAD_SHAKE_TIME);
	}

	@Override
	public void setHeadShakeTimer(int time) {
		metaContainer.get(META_HEAD_SHAKE_TIME).setData(time);		
	}

	@Override
	public MerchantInventory getInventory() {
		if (inv == null)
			inv = ContainerFactory.MERCHANT_INV_FACTORY.create(InventoryType.MERCHANT, this);
		return inv;
	}

}
