package de.atlascore.entity;

import java.util.ArrayList;
import java.util.List;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Pillager;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;

public class CorePillager extends CoreRaider implements Pillager {

	protected static final MetaDataField<Boolean>
	META_IS_CHARGING = new MetaDataField<>(CoreRaider.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreRaider.LAST_META_INDEX+1;
	
	private List<ItemStack> pocketItems;
	
	public CorePillager(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_CHARGING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isCharging() {
		return metaContainer.getData(META_IS_CHARGING);
	}

	@Override
	public void setCharging(boolean charging) {
		metaContainer.get(META_IS_CELEBRATING).setData(charging);
	}

	@Override
	public List<ItemStack> getPocketItems() {
		if (pocketItems == null)
			pocketItems = new ArrayList<>();
		return pocketItems;
	}

	@Override
	public boolean hasPocketItems() {
		return pocketItems != null && !pocketItems.isEmpty();
	}

}
