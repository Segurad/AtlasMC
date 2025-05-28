package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.Cat;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCat extends CoreTameable implements Cat {
	
	protected static final MetaDataField<Integer>
	META_CAT_TYPE = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, 1, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_IS_LYING = new MetaDataField<>(CoreTameable.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_RELAXED = new MetaDataField<>(CoreTameable.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_COLLAR_COLOR = new MetaDataField<>(CoreTameable.LAST_META_INDEX+4, DyeColor.RED.getID(), MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+4;
	
	protected static final NBTFieldSet<CoreCat> NBT_FIELDS;
	
	protected static final CharKey
	NBT_VARIANT = CharKey.literal("variant"), 
	NBT_COLLAR_COLOR = CharKey.literal("CollarColor");
	
	static {
		NBT_FIELDS = CoreTameable.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_VARIANT, (holder, reader) -> {
			holder.setCatType(Type.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_COLLAR_COLOR, (holder, reader) -> {
			holder.setCollarColor(DyeColor.getByID(reader.readByteTag()));
		});
	}
	
	public CoreCat(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreCat> getFieldSetRoot() {
		return NBT_FIELDS;
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
		return Type.getByID(metaContainer.getData(META_CAT_TYPE));
	}

	@Override
	public void setCatType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_CAT_TYPE).setData(type.getID());
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
		return DyeColor.getByID(metaContainer.getData(META_COLLAR_COLOR));
	}

	@Override
	public void setCollarColor(DyeColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		metaContainer.get(META_COLLAR_COLOR).setData(color.getID());		
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(NBT_VARIANT, getCatType().getID());
		writer.writeByteTag(NBT_COLLAR_COLOR, getCollarColor().getID());
	}

}
