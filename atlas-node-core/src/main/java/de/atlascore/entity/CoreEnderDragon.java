package de.atlascore.entity;

import de.atlasmc.entity.EnderDragon;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreEnderDragon extends CoreMob implements EnderDragon {

	protected static final MetaDataField<Integer>
	META_DRAGON_PHASE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, DragonPhase.HOVERING.getID(), MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreEnderDragon(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DRAGON_PHASE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public DragonPhase getPhase() {
		return DragonPhase.getByID(metaContainer.getData(META_DRAGON_PHASE));
	}

	@Override
	public void setPhase(DragonPhase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase can not be null!");
		metaContainer.get(META_DRAGON_PHASE).setData(phase.getID());
	}

}
