package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Hoglin;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreHoglin extends CoreAgeableMob implements Hoglin {

	protected static final MetaDataField<Boolean>
	META_IMMUNE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_IS_IMMUNE_TO_ZOMBIFICATION = "IsImmuneToZombiefication",
	// NBT_TIME_IN_OVERWORLD = "TimeInOverworld", TODO skipped until usecase
	NBT_CANNOT_BE_HUNTED = "CannotBeHunted";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAgeableMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_IS_IMMUNE_TO_ZOMBIFICATION, (holder, reader) -> {
			if (holder instanceof Hoglin) {
				((Hoglin) holder).setImmune(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_CANNOT_BE_HUNTED, (holder, reader) -> {
			if (holder instanceof Hoglin) {
				((Hoglin) holder).setHuntable(reader.readByteTag() == 0);
			} else reader.skipTag();
		});
	}
	
	private boolean huntable = true;
	
	public CoreHoglin(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
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
