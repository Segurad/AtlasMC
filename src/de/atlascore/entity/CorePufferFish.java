package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PufferFish;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CorePufferFish extends CoreFish implements PufferFish {

	protected static final MetaDataField<Integer>
	META_PUFF_STATE = new MetaDataField<>(CoreFish.LAST_META_INDEX+1, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreFish.LAST_META_INDEX+1;
	
	public CorePufferFish(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
