package de.atlasmc.core.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Cat;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreCat extends CoreTameable implements Cat {
	
	protected static final MetaDataField<Type>
	META_CAT_TYPE = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, Type.BLACK, MetaDataType.getVarIntEnumType(Type.class));
	protected static final MetaDataField<Boolean>
	META_IS_LYING = new MetaDataField<>(CoreTameable.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_RELAXED = new MetaDataField<>(CoreTameable.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<DyeColor>
	META_COLLAR_COLOR = new MetaDataField<>(CoreTameable.LAST_META_INDEX+4, DyeColor.RED, MetaDataType.getVarIntEnumType(DyeColor.class));
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+4;
	
	public CoreCat(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_CAT_TYPE);
		metaContainer.set(META_IS_LYING);
		metaContainer.set(META_IS_RELAXED);
		metaContainer.set(META_COLLAR_COLOR);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Type getCatType() {
		return metaContainer.getData(META_CAT_TYPE);
	}

	@Override
	public void setCatType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_CAT_TYPE).setData(type);
	}

	@Override
	public boolean isLying() {
		return metaContainer.getData(META_IS_LYING);
	}

	@Override
	public void setLying(boolean lying) {
		metaContainer.get(META_IS_LYING).setData(lying);		
	}

	@Override
	public boolean isRelaxed() {
		return metaContainer.getData(META_IS_RELAXED);
	}

	@Override
	public void setRelaxed(boolean relaxed) {
		metaContainer.get(META_IS_RELAXED).setData(relaxed);		
	}

	@Override
	public DyeColor getCollarColor() {
		return metaContainer.getData(META_COLLAR_COLOR);
	}

	@Override
	public void setCollarColor(DyeColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		metaContainer.get(META_COLLAR_COLOR).setData(color);		
	}

}
