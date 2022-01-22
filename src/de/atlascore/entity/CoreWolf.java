package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Wolf;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreWolf extends CoreTameable implements Wolf {

	protected static final MetaDataField<Boolean>
	META_IS_BEGGING = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_COLLAR_COLOR = new MetaDataField<>(CoreTameable.LAST_META_INDEX+2, DyeColor.RED.getID(), MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_ANGER_TIME = new MetaDataField<>(CoreTameable.LAST_META_INDEX+3, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+3;
	
	public CoreWolf(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BEGGING);
		metaContainer.set(META_COLLAR_COLOR);
		metaContainer.set(META_ANGER_TIME);
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
		metaContainer.get(META_IS_BEGGING).setData(begging);		
	}

	@Override
	public DyeColor getCollarColor() {
		return DyeColor.getByID(metaContainer.getData(META_COLLAR_COLOR));
	}

	@Override
	public void setCollarColor(DyeColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		metaContainer.get(META_COLLAR_COLOR).setData(color.getID());
	}

	@Override
	public int getAnger() {
		return metaContainer.getData(META_ANGER_TIME);
	}

	@Override
	public void setAnger(int anger) {
		metaContainer.get(META_ANGER_TIME).setData(anger);		
	}

}
