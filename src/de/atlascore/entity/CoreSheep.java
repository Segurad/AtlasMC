package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Sheep;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreSheep extends CoreAgeableMob implements Sheep {

	protected static final MetaDataField<Byte>
	META_SHEEP_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_COLOR = "Color",
	NBT_SHEARED = "Sheared";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAgeableMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_COLOR, (holder, reader) -> {
			if (holder instanceof Sheep) {
				((Sheep) holder).setColor(DyeColor.getByID(reader.readByteTag()));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_SHEARED, (holder, reader) -> {
			if (holder instanceof Sheep) {
				((Sheep) holder).setSheared(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
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

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(NBT_COLOR, getColor().getID());
		if (isSheared())
			writer.writeByteTag(NBT_SHEARED, true);
	}
	
}
