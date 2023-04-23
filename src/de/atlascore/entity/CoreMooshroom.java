package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Mooshroom;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreMooshroom extends CoreCow implements Mooshroom {

	protected static final MetaDataField<String>
	META_SHROOM_TYPE = new MetaDataField<>(CoreCow.LAST_META_INDEX+1, Variant.RED.getNameID(), MetaDataType.STRING);
	
	protected static final int LAST_META_INDEX = CoreCow.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_TYPE = CharKey.of("Type");
	//NBT_EFFECT_ID = "EffectId", TODO unnecessary
	//NBT_EFFECT_DURATION = "EffectDuration";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreCow.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_TYPE, (holder, reader) -> {
			if (holder instanceof Mooshroom) {
				((Mooshroom) holder).setVariant(Variant.getByNameID(reader.readStringTag()));
			} else reader.skipTag();
		});
	}
	
	public CoreMooshroom(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SHROOM_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Variant getVariant() {
		return Variant.getByNameID(metaContainer.getData(META_SHROOM_TYPE));
	}

	@Override
	public void setVariant(Variant variant) {
		if (variant == null)
			throw new IllegalArgumentException("Variant can not be null!");
		metaContainer.get(META_SHROOM_TYPE).setData(variant.getNameID());
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(NBT_TYPE, getVariant().getNameID());
	}

}
