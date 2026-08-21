package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Parrot;
import de.atlasmc.util.enums.EnumUtil;

public class CoreParrot extends CoreTameable implements Parrot {

	protected static final MetaDataField<Integer>
	META_PARROT_TYPE = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, Type.RED_BLUE.getID(), EntityMetaTypes.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+1;
	
	public CoreParrot(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PARROT_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Type getParrotType() {
		return EnumUtil.getByID(Type.class, metaContainer.getData(META_PARROT_TYPE));
	}

	@Override
	public void setParrotType(Type type) {
		metaContainer.setData(META_PARROT_TYPE, type.getID());
	}

}
