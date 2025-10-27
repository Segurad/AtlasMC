package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Merchant;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.MerchantInventory;

public class CoreMerchant extends CoreAgeableMob implements Merchant {

	protected static final MetaDataField<Integer>
	META_HEAD_SHAKE_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
		
	private MerchantInventory inv;
	
	public CoreMerchant(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HEAD_SHAKE_TIME);
	}
	
	public static int getLastMetaIndex() {
		return LAST_META_INDEX + 1;
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
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

}
