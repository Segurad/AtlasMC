package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Mooshroom;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.util.enums.EnumUtil;

public class CoreMooshroom extends CoreAgeableMob implements Mooshroom {

	protected static final MetaDataField<String>
	META_SHROOM_TYPE = new MetaDataField<>(CoreCow.LAST_META_INDEX+1, Variant.RED.getName(), MetaDataType.STRING);
	
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
		return EnumUtil.getByName(Variant.class, metaContainer.getData(META_SHROOM_TYPE));
	}

	@Override
	public void setVariant(Variant variant) {
		if (variant == null)
			throw new IllegalArgumentException("Variant can not be null!");
		metaContainer.get(META_SHROOM_TYPE).setData(variant.getName());
	}

}
