package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Cat;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.util.enums.EnumUtil;

public class CoreCat extends CoreTameable implements Cat {
	
	protected static final MetaDataField<Type>
	META_CAT_TYPE = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, Type.BLACK, EntityMetaTypes.CAT_VARIANT);
	protected static final MetaDataField<Boolean>
	META_IS_LYING = new MetaDataField<>(CoreTameable.LAST_META_INDEX+2, false, EntityMetaTypes.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_RELAXED = new MetaDataField<>(CoreTameable.LAST_META_INDEX+3, false, EntityMetaTypes.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_COLLAR_COLOR = new MetaDataField<>(CoreTameable.LAST_META_INDEX+4, DyeColor.RED.getID(), EntityMetaTypes.VAR_INT);
	
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
		metaContainer.setData(META_CAT_TYPE, type);
	}

	@Override
	public boolean isLying() {
		return metaContainer.getData(META_IS_LYING);
	}

	@Override
	public void setLying(boolean lying) {
		metaContainer.setData(META_IS_LYING, lying);		
	}

	@Override
	public boolean isRelaxed() {
		return metaContainer.getData(META_IS_RELAXED);
	}

	@Override
	public void setRelaxed(boolean relaxed) {
		metaContainer.setData(META_IS_RELAXED, relaxed);		
	}

	@Override
	public DyeColor getCollarColor() {
		return EnumUtil.getByID(DyeColor.class, metaContainer.getData(META_COLLAR_COLOR));
	}

	@Override
	public void setCollarColor(DyeColor color) {
		metaContainer.setData(META_COLLAR_COLOR, color.getID());		
	}

}
