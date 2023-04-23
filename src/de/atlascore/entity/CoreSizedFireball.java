package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SizedFireball;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;

public abstract class CoreSizedFireball extends CoreAbstractFireball implements SizedFireball {

	protected static final MetaDataField<ItemStack>
	META_FIREBALL_ITEM = new MetaDataField<>(CoreAbstractFireball.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	
	protected static final int LAST_META_INDEX = CoreAbstractFireball.LAST_META_INDEX+1;
	
	public CoreSizedFireball(EntityType type, UUID uuid) {
		super(type, uuid);
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
	public ItemStack getDisplayItem() {
		return metaContainer.getData(META_FIREBALL_ITEM);
	}

	@Override
	public void setDisplayItem(ItemStack item) {
		metaContainer.get(META_FIREBALL_ITEM).setData(item);
	}

}
