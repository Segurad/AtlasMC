package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.SimpleLocation;
import de.atlasmc.entity.Dolphin;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreDolphin extends CoreMob implements Dolphin {

	protected static final MetaDataField<Long>
	META_TREASURE_POSITION = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0L, MetaDataType.POSITION);
	protected static final MetaDataField<Boolean>
	META_HAS_FISH = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_MOISTURE_LEVEL = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, 2400, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;
	
	protected static final CharKey
	NBT_CAN_PICKUP_LOOT = CharKey.of("CanPickUpLoot"),
	NBT_GOT_FISH = CharKey.of("GotFish"),
	NBT_MOISTNESS = CharKey.of("Moistness"),
	// TODO currently skipped POS x y z
	NBT_TREASURE_POS_X = CharKey.of("TreasurePosX"),
	NBT_TREASURE_POS_Y = CharKey.of("TreasurePosY"),
	NBT_TREASURE_POS_Z = CharKey.of("TreasurePosZ");

	static {
		NBT_FIELDS.setField(NBT_CAN_PICKUP_LOOT, (holder, reader) -> {
			if (holder instanceof Dolphin) {
				((Dolphin) holder).setCanPickupLoot(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_GOT_FISH, (holder, reader) -> {
			if (holder instanceof Dolphin) {
				((Dolphin) holder).setFish(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_MOISTNESS, (holder, reader) -> {
			if (holder instanceof Dolphin) {
				((Dolphin) holder).setMoistureLevel(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	private boolean canPickupLoot;
	
	public CoreDolphin(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TREASURE_POSITION);
		metaContainer.set(META_HAS_FISH);
		metaContainer.set(META_MOISTURE_LEVEL);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasFish() {
		return metaContainer.getData(META_HAS_FISH);
	}

	@Override
	public SimpleLocation getTreasurePosition(SimpleLocation loc) {
		return MathUtil.getLocation(loc, metaContainer.getData(META_TREASURE_POSITION));
	}

	@Override
	public void setTreasurePosition(int x, int y, int z) {
		metaContainer.get(META_TREASURE_POSITION).setData(MathUtil.toPosition(x, y, z));
	}

	@Override
	public void setFish(boolean fish) {
		metaContainer.get(META_HAS_FISH).setData(fish);
	}

	@Override
	public int getMoistureLevel() {
		return metaContainer.getData(META_MOISTURE_LEVEL);
	}

	@Override
	public void setMoistureLevel(int level) {
		if (level > 2400 || level < 0) throw new IllegalArgumentException("Level is not between 0 and 2400: " + level);
		metaContainer.get(META_MOISTURE_LEVEL).setData(level);
	}

	@Override
	public int getMaxMoistureLevel() {
		return 2400;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (canPickupLoot())
			writer.writeByteTag(NBT_CAN_PICKUP_LOOT, true);
		if (hasFish())
			writer.writeByteTag(NBT_GOT_FISH, true);
		writer.writeIntTag(NBT_MOISTNESS, getMoistureLevel());
	}

	@Override
	public boolean canPickupLoot() {
		return canPickupLoot;
	}

	@Override
	public void setCanPickupLoot(boolean canPickup) {
		this.canPickupLoot = canPickup;
	}

}
