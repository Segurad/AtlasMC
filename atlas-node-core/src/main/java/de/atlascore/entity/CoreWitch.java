package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Witch;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreWitch extends CoreRaider implements Witch {

	protected static final MetaDataField<Boolean>
	META_IS_DRINKING_POTION = new MetaDataField<>(CoreRaider.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreRaider.LAST_META_INDEX+1;
	
	public CoreWitch(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_DRINKING_POTION);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isDrinkingPotion() {
		return metaContainer.getData(META_IS_DRINKING_POTION);
	}

	@Override
	public void setDrinkingPotion(boolean drinking) {
		metaContainer.get(META_IS_DRINKING_POTION).setData(drinking);		
	}

}
