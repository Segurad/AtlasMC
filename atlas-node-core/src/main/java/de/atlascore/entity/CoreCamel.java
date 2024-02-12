package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Camel;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCamel extends CoreAbstractHorse implements Camel {

	protected static final MetaDataField<Boolean> META_DASHING = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Long> META_LAST_POSE_TICK = new MetaDataField<>(CoreAbstractHorse.LAST_META_INDEX+2, 0L, MetaDataType.VAR_LONG);
	
	protected static final int LAST_META_INDEX = CoreAbstractHorse.LAST_META_INDEX+2;
	
	protected static final NBTFieldContainer<CoreCamel> NBT_FIELDS;
	
	private static final CharKey LAST_POSE_TICK = CharKey.literal("LastPoseTick");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreAbstractHorse.NBT_FIELDS);
		NBT_FIELDS.setField(LAST_POSE_TICK, (holder, reader) -> {
			holder.setLastPoseTick(reader.readLongTag());
		});
	}
	
	public CoreCamel(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DASHING);
		metaContainer.set(META_LAST_POSE_TICK);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreCamel> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public long getLastPoseTick() {
		return metaContainer.getData(META_LAST_POSE_TICK);
	}

	@Override
	public void setLastPoseTick(long pose) {
		metaContainer.get(META_LAST_POSE_TICK).setData(pose);
	}

	@Override
	public boolean isDashing() {
		return metaContainer.getData(META_DASHING);
	}

	@Override
	public void setDashing(boolean dashing) {
		metaContainer.get(META_DASHING).setData(dashing);
	}

	@Override
	protected AbstractHorseInventory createInventory() {
		return (AbstractHorseInventory) InventoryType.HORSE.create(this);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		long lastPose = getLastPoseTick();
		if (lastPose != 0)
			writer.writeLongTag(LAST_POSE_TICK, lastPose);
	}

}
