package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Parrot;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreParrot extends CoreTameable implements Parrot {

	protected static final MetaDataField<Integer>
	META_PARROT_TYPE = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreParrot> NBT_FIELDS;
	
	protected static final CharKey
	NBT_VARIANT = CharKey.literal("Variant");
	
	static {
		NBT_FIELDS = CoreTameable.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_VARIANT, (holder, reader) -> {
			holder.setParrotType(Type.getByID(reader.readIntTag()));
		});
	}
	
	public CoreParrot(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreParrot> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PARROT_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Type getParrotType() {
		return Type.getByID(metaContainer.getData(META_PARROT_TYPE));
	}

	@Override
	public void setParrotType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_PARROT_TYPE).setData(type.getID());
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_VARIANT, getParrotType().getID());
	}

}
