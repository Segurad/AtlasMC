package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.ThrowableProjectile;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.inventory.ItemStack;

public abstract class CoreThrowableProjectile extends CoreAbstractProjectile implements ThrowableProjectile {
	
	protected static final MetaDataField<ItemStack> 
	META_PROJECTILE_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX + 1, null, MetaDataType.SLOT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	public CoreThrowableProjectile(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PROJECTILE_ITEM);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX + 1;
	}

	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_PROJECTILE_ITEM);
	}

	@Override
	public void setItem(ItemStack item) {
		metaContainer.get(META_PROJECTILE_ITEM).setData(item);
	}
	
}
