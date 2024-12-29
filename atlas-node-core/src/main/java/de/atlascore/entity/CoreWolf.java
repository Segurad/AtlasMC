package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Wolf;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreWolf extends CoreTameable implements Wolf {

	protected static final MetaDataField<Boolean>
	META_IS_BEGGING = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_COLLAR_COLOR = new MetaDataField<>(CoreTameable.LAST_META_INDEX+2, DyeColor.RED.getID(), MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_ANGER_TIME = new MetaDataField<>(CoreTameable.LAST_META_INDEX+3, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<WolfVariant>
	META_WOLF_VARIANT = new MetaDataField<>(CoreTameable.LAST_META_INDEX+4, null, MetaDataType.WOLF_VARIANT);
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+4;
	
	protected static final NBTFieldContainer<CoreWolf> NBT_FIELDS;
	
	protected static final CharKey
	NBT_ANGRY = CharKey.literal("Angry"),
	NBT_COLLAR_COLOR = CharKey.literal("CollarColor");
	
	static {
		NBT_FIELDS = CoreTameable.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_COLLAR_COLOR, (holder, reader) -> {
			holder.setCollarColor(DyeColor.getByID(reader.readByteTag()));
		});
		NBT_FIELDS.setField(NBT_ANGRY, (holder, reader) -> {
			holder.setAngry(reader.readByteTag() == 1);
		});
	}
	
	private int angerTicks = -1;
	
	public CoreWolf(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreWolf> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BEGGING);
		metaContainer.set(META_COLLAR_COLOR);
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
	public boolean isAngry() {
		return metaContainer.getData(META_ANGER_TIME) == 1;
	}

	@Override
	public void setAngry(boolean angry) {
		metaContainer.get(META_ANGER_TIME).setData(angry ? 1 : 0);
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isAngry())
			writer.writeByteTag(NBT_ANGRY, true);
		writer.writeByteTag(NBT_COLLAR_COLOR, getCollarColor().getID());
	}

	@Override
	public WolfVariant getVariant() {
		return metaContainer.getData(META_WOLF_VARIANT);
	}

	@Override
	public void setVariant(WolfVariant variant) {
		metaContainer.get(META_WOLF_VARIANT).setData(variant);
	}

}
