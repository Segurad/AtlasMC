package de.atlasmc.core.node.entity;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Piglin;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.node.inventory.ItemStack;

public class CorePiglin extends CoreAbstractPiglin implements Piglin {
	
	protected static final MetaDataField<Boolean>
	META_IS_BABY = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_CHARGING_CROSSBOW = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_DANCING = new MetaDataField<>(CoreAbstractPiglin.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);

	protected static final int LAST_META_INDEX = CoreAbstractPiglin.LAST_META_INDEX+3;
	
	private List<ItemStack> pocketItems;
	private boolean canHunt;
	
	public CorePiglin(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BABY);
		metaContainer.set(META_IS_CHARGING_CROSSBOW);
		metaContainer.set(META_IS_DANCING);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isBaby() {
		return metaContainer.getData(META_IS_BABY);
	}

	@Override
	public boolean isChargingCrossbow() {
		return metaContainer.getData(META_IS_CHARGING_CROSSBOW);
	}

	@Override
	public boolean isDancing() {
		return metaContainer.getData(META_IS_SILENT);
	}

	@Override
	public void setBaby(boolean baby) {
		metaContainer.get(META_IS_BABY).setData(baby);		
	}

	@Override
	public void setChargingCorssbow(boolean charging) {
		metaContainer.get(META_IS_CHARGING_CROSSBOW).setData(charging);		
	}

	@Override
	public void setDancing(boolean dancing) {
		metaContainer.get(META_IS_DANCING).setData(dancing);		
	}

	@Override
	public void setCannotHunt(boolean hunt) {
		this.canHunt = hunt;
	}

	@Override
	public boolean cannotHunt() {
		return canHunt;
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

	@Override
	public void addPocketItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getPocketItems().add(item);
	}

	@Override
	public void removePocketItem(ItemStack item) {
		if (!hasPocketItems())
			return;
		getPocketItems().remove(item);
	}

}
