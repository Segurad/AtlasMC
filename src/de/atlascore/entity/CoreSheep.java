package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Sheep;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreSheep extends CoreAgeableMob implements Sheep {

	protected static final MetaDataField<Byte>
	META_SHEEP_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CoreSheep(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
		return DyeColor.getByID(metaContainer.getData(META_SHEEP_FLAGS) & 0xF);
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
