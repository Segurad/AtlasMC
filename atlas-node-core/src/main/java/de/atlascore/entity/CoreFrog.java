package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Frog;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFrog extends CoreAgeableMob implements Frog {

	protected static final MetaDataField<Variant> META_FROG_VARIANT = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, Variant.TEMPERATE, MetaDataType.FROG_VARIANT);
	protected static final MetaDataField<Integer> META_TONGUE_TARGET = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.OPT_VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	protected static final NBTFieldContainer<CoreFrog> NBT_FIELDS;
	
	private static final CharKey NBT_VARIANT = CharKey.literal("variant");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreAgeableMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_VARIANT, (holder, reader) -> {
			holder.setVariant(Variant.getByNameID(reader.readStringTag()));
		});
	}
	
	private Entity tongueTarget;
	
	public CoreFrog(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FROG_VARIANT);
		metaContainer.set(META_TONGUE_TARGET);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreLivingEntity> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public Variant getVariant() {
		return metaContainer.getData(META_FROG_VARIANT);
	}

	@Override
	public void setVariant(Variant variant) {
		metaContainer.get(META_FROG_VARIANT).setData(variant);
	}

	@Override
	public Entity getTongueTarget() {
		return tongueTarget;
	}

	@Override
	public void setTangueTarget(Entity entity) {
		metaContainer.get(META_TONGUE_TARGET).setData(entity != null ? entity.getID() : null);
		this.tongueTarget = entity;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		Variant variant = getVariant();
		if (META_FROG_VARIANT.getDefaultData() != variant)
			writer.writeStringTag(NBT_VARIANT, variant.getNameID());
	}

}
