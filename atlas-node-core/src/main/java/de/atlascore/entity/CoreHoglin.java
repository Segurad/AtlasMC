package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Hoglin;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreHoglin extends CoreAgeableMob implements Hoglin {

	protected static final MetaDataField<Boolean>
	META_IMMUNE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreHoglin> NBT_FIELDS;
	
	protected static final CharKey
	NBT_IS_IMMUNE_TO_ZOMBIFICATION = CharKey.literal("IsImmuneToZombiefication"),
	// NBT_TIME_IN_OVERWORLD = "TimeInOverworld", TODO skipped until usecase
	NBT_CANNOT_BE_HUNTED = CharKey.literal("CannotBeHunted");
	
	static {
		NBT_FIELDS = CoreAgeableMob.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_IS_IMMUNE_TO_ZOMBIFICATION, (holder, reader) -> {
			holder.setImmune(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_CANNOT_BE_HUNTED, (holder, reader) -> {
			holder.setHuntable(reader.readByteTag() == 0);
		});
	}
	
	private boolean huntable = true;
	
	public CoreHoglin(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreHoglin> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IMMUNE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isImmune() {
		return metaContainer.getData(META_IMMUNE);
	}

	@Override
	public void setImmune(boolean immune) {
		metaContainer.get(META_IMMUNE).setData(immune);	
	}

	@Override
	public void setHuntable(boolean huntable) {
		this.huntable = huntable;	
	}

	@Override
	public boolean isHuntable() {
		return huntable;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isImmune())
			writer.writeByteTag(NBT_IS_IMMUNE_TO_ZOMBIFICATION, true);
		if (!isHuntable())
			writer.writeByteTag(NBT_CANNOT_BE_HUNTED, true);
	}
	
}
