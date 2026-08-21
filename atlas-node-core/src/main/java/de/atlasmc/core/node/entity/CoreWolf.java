package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Wolf;
import de.atlasmc.util.enums.EnumUtil;

public class CoreWolf extends CoreTameable implements Wolf {

	protected static final MetaDataField<Boolean>
	META_IS_BEGGING = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, false, EntityMetaTypes.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_COLAR_COLOR = new MetaDataField<>(CoreTameable.LAST_META_INDEX+2, DyeColor.RED.getID(), EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Integer>
	META_ANGER_TIME = new MetaDataField<>(CoreTameable.LAST_META_INDEX+3, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<WolfVariant>
	META_WOLF_VARIANT = new MetaDataField<>(CoreTameable.LAST_META_INDEX+4, null, EntityMetaTypes.WOLF_VARIANT);
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+4;
	
	private int angerTicks = -1;
	
	public CoreWolf(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BEGGING);
		metaContainer.set(META_COLAR_COLOR);
		metaContainer.set(META_ANGER_TIME);
		metaContainer.set(META_WOLF_VARIANT);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isBegging() {
		return metaContainer.getData(META_IS_BEGGING);
	}

	@Override
	public void setBegging(boolean begging) {
		metaContainer.setData(META_IS_BEGGING, begging);		
	}

	@Override
	public DyeColor getCollarColor() {
		return EnumUtil.getByID(DyeColor.class, metaContainer.getData(META_COLAR_COLOR));
	}

	@Override
	public void setCollarColor(DyeColor color) {
		metaContainer.setData(META_COLAR_COLOR, color.getID());
	}

	@Override
	public boolean isAngry() {
		return metaContainer.getData(META_ANGER_TIME) == 1;
	}

	@Override
	public void setAngry(boolean angry) {
		metaContainer.setData(META_ANGER_TIME, angry ? 1 : 0);
	}

	@Override
	public int getAngerTime() {
		return angerTicks;
	}

	@Override
	public void setAngerTime(int anger) {
		this.angerTicks = anger;
	}

	@Override
	public WolfVariant getVariant() {
		return metaContainer.getData(META_WOLF_VARIANT);
	}

	@Override
	public void setVariant(WolfVariant variant) {
		metaContainer.setData(META_WOLF_VARIANT, variant);
	}

}
