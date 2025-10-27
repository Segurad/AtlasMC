package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Parrot;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreParrot extends CoreTameable implements Parrot {

	protected static final MetaDataField<Type>
	META_PARROT_TYPE = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, Type.RED_BLUE, MetaDataType.getVarIntEnumType(Type.class));
	
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
		return metaContainer.getData(META_PARROT_TYPE);
	}

	@Override
	public void setParrotType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_PARROT_TYPE).setData(type);
	}

}
