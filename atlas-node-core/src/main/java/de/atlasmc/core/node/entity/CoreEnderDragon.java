package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EnderDragon;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreEnderDragon extends CoreMob implements EnderDragon {

	protected static final MetaDataField<DragonPhase>
	META_DRAGON_PHASE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, DragonPhase.HOVERING, MetaDataType.getVarIntEnumType(DragonPhase.class));
	
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
		return metaContainer.getData(META_DRAGON_PHASE);
	}

	@Override
	public void setPhase(DragonPhase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase can not be null!");
		metaContainer.get(META_DRAGON_PHASE).setData(phase);
	}

}
