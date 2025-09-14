package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Ocelot;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreOcelot extends CoreAgeableMob implements Ocelot {

	protected static final MetaDataField<Boolean>
	META_OCELOT_TRUSTING = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CoreOcelot(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_OCELOT_TRUSTING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isTrusting() {
		return metaContainer.getData(META_OCELOT_TRUSTING);
	}

	@Override
	public void setTrusting(boolean trusting) {
		metaContainer.get(META_OCELOT_TRUSTING).setData(trusting);
	}

}
