package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Mooshroom;
import de.atlasmc.util.enums.EnumUtil;

public class CoreMooshroom extends CoreAgeableMob implements Mooshroom {

	protected static final MetaDataField<Integer>
	META_SHROOM_TYPE = new MetaDataField<>(CoreCow.LAST_META_INDEX+1, Variant.RED.getID(), EntityMetaTypes.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreCow.LAST_META_INDEX+1;
	
	public CoreMooshroom(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SHROOM_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Variant getVariant() {
		return EnumUtil.getByID(Variant.class, metaContainer.getData(META_SHROOM_TYPE));
	}

	@Override
	public void setVariant(Variant variant) {
		metaContainer.setData(META_SHROOM_TYPE, variant.getID());
	}

}
