package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.SizedFireball;
import de.atlasmc.node.inventory.ItemStack;

public abstract class CoreSizedFireball extends CoreAbstractAcceleratingProjectile implements SizedFireball {

	protected static final MetaDataField<ItemStack>
	META_FIREBALL_ITEM = new MetaDataField<>(CoreAbstractAcceleratingProjectile.LAST_META_INDEX+1, null, EntityMetaTypes.SLOT);
	
	protected static final int LAST_META_INDEX = CoreAbstractAcceleratingProjectile.LAST_META_INDEX+1;
	
	public CoreSizedFireball(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FIREBALL_ITEM);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_FIREBALL_ITEM);
	}

	@Override
	public void setItem(ItemStack item) {
		metaContainer.setData(META_FIREBALL_ITEM, item);
	}

}
