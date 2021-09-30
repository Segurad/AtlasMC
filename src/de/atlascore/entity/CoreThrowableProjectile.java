package de.atlascore.entity;

import de.atlasmc.Location;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ThrowableProjectile;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;

import java.util.UUID;

public abstract class CoreThrowableProjectile extends CoreEntity implements ThrowableProjectile {
	
	protected static final int
	META_PROJECTILE_ITEM = 8;
	
	public CoreThrowableProjectile(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<ItemStack>(META_PROJECTILE_ITEM, MetaDataType.SLOT));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return super.getMetaContainerSize() + 1;
	}

	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_PROJECTILE_ITEM, MetaDataType.SLOT);
	}

	@Override
	public void setItem(ItemStack item) {
		metaContainer.get(META_PROJECTILE_ITEM, MetaDataType.SLOT).setData(item);
	}

	@Override
	public abstract ProjectileType getProjectileType();

}
