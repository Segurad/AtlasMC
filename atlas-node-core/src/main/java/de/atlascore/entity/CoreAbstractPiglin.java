package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.AbstractPiglin;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAbstractPiglin extends CoreMob implements AbstractPiglin {

	protected static final MetaDataField<Boolean>
	META_IMMUNE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer<CoreAbstractPiglin> NBT_FIELDS;
	
	protected static final CharKey
	NBT_IS_IMMUNE_TO_ZOMBIFICATION = CharKey.literal("IsImmuneToZombification");
	// NBT_TIME_IN_OVERWORLD = "TimeInOverworld"; TODO unnecessary
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_IS_IMMUNE_TO_ZOMBIFICATION, (holder, reader) -> {
			holder.setImmune(reader.readByteTag() == 1);
		});
	}
	
	public CoreAbstractPiglin(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreAbstractPiglin> getFieldContainerRoot() {
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isImmune())
			writer.writeByteTag(NBT_IS_IMMUNE_TO_ZOMBIFICATION, true);
	}
	
}
