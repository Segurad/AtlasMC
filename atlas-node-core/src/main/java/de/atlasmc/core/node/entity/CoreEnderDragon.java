package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EnderDragon;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.util.enums.EnumUtil;

public class CoreEnderDragon extends CoreMob implements EnderDragon {

	protected static final MetaDataField<Integer>
	META_DRAGON_PHASE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, DragonPhase.HOVERING.getID(), EntityMetaTypes.VAR_INT);
	
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
		return EnumUtil.getByID(DragonPhase.class, metaContainer.getData(META_DRAGON_PHASE));
	}

	@Override
	public void setPhase(DragonPhase phase) {
		metaContainer.setData(META_DRAGON_PHASE, phase.getID());
	}

}
