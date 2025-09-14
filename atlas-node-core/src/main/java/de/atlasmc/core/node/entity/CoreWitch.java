package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Witch;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

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
