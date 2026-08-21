package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Guardian;

public class CoreGuardian extends CoreMob implements Guardian {

	protected static final MetaDataField<Boolean>
	META_RETRACTING_SPIKES = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, EntityMetaTypes.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_TARGET_EID = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, 0, EntityMetaTypes.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+2;
	
	private Entity target;
	
	public CoreGuardian(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_RETRACTING_SPIKES);
		metaContainer.set(META_TARGET_EID);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isRetractingSpikes() {
		return metaContainer.getData(META_RETRACTING_SPIKES);
	}

	@Override
	public Entity getTarget() {
		return target;
	}

	@Override
	public void setRetractingSpikes(boolean retracting) {
		metaContainer.setData(META_RETRACTING_SPIKES, retracting);
	}

	@Override
	public boolean hasTarget() {
		return target != null;
	}

	@Override
	public void setTarget(Entity target) {
		if (target == null)
			metaContainer.setData(META_TARGET_EID, 0);
		else {
			if (target.isRemoved())
				throw new IllegalArgumentException("Target must be a removed Entity!");
			metaContainer.setData(META_TARGET_EID, target.getID());
		}
		this.target = target;
	}

}
