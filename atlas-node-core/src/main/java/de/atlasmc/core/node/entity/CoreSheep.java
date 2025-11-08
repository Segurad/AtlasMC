package de.atlasmc.core.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Sheep;
import de.atlasmc.node.entity.metadata.type.MetaData;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.util.enums.EnumUtil;

public class CoreSheep extends CoreAgeableMob implements Sheep {

	protected static final MetaDataField<Byte>
	META_SHEEP_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) DyeColor.WHITE.getID(), MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CoreSheep(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SHEEP_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public DyeColor getColor() {
		return EnumUtil.getByID(DyeColor.class, metaContainer.getData(META_SHEEP_FLAGS) & 0xF);
	}

	@Override
	public void setColor(DyeColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		MetaData<Byte> data = metaContainer.get(META_SHEEP_FLAGS);
		data.setData((byte) ((data.getData() & 0xF0) | color.getID()));
	}

	@Override
	public boolean isSheared() {
		return (metaContainer.getData(META_SHEEP_FLAGS) & 0x10) == 0x10;
	}

	@Override
	public void setSheared(boolean sheared) {
		MetaData<Byte> data = metaContainer.get(META_SHEEP_FLAGS);
		data.setData((byte) (sheared ? data.getData() | 0x10 : data.getData() & 0xEF));
	}
	
}
