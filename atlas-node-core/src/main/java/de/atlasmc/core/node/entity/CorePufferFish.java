package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.PufferFish;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CorePufferFish extends CoreFish implements PufferFish {

	protected static final MetaDataField<Integer>
	META_PUFF_STATE = new MetaDataField<>(CoreFish.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreFish.LAST_META_INDEX+1;
	
	public CorePufferFish(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PUFF_STATE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public int getPuffState() {
		return metaContainer.getData(META_PUFF_STATE);
	}

	@Override
	public void setPuffState(int state) {
		if (state > 2 || state < 0) throw new IllegalArgumentException("State is not between 0 and 2: " + state);
		metaContainer.get(META_PUFF_STATE).setData(state);		
	}

}
