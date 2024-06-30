package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.entity.Bee;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBee extends CoreAgeableMob implements Bee {

	/**
	 * 0x02 - is angry<br>
	 * 0x04 - has stung<br>
	 * 0x08 - has nectar<br>
	 */
	protected static final MetaDataField<Byte>
	META_BEE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Integer>
	META_BEE_ANGER_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	protected static final CharKey
	NBT_HIVE_POS = CharKey.literal("HivePos"),
	NBT_FLOWER_POS = CharKey.literal("FlowerPos"),
	NBT_HAS_NECTAR = CharKey.literal("HasNextar"),
	NBT_HAS_STUNG = CharKey.literal("HasStung"),
	NBT_TICKS_SINCE_POLLINATION = CharKey.literal("TicksSincePollination"),
	NBT_CANNOT_ENTER_HIVE_TICKS = CharKey.literal("CannotEnterHiveTicks"),
	NBT_CROPS_GROWN_SINCE_POLLINATION = CharKey.literal("CropsGrownSincePollination"),
	NBT_ANGER = CharKey.literal("Anger"),
	NBT_HURT_BY = CharKey.literal("HurtBy"),
	NBT_X = CharKey.literal("X"),
	NBT_Y = CharKey.literal("Y"),
	NBT_Z = CharKey.literal("Z");
	
	static {
		NBT_FIELDS.setField(NBT_HIVE_POS, (holder, reader) -> {
			if (holder instanceof Bee) {
				reader.readNextEntry();
				int x = 0;
				int y = 0;
				int z = 0;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (NBT_X.equals(value))
						x = reader.readIntTag();
					else if (NBT_Y.equals(value))
						y = reader.readIntTag();
					else if (NBT_Z.equals(value))
						z = reader.readIntTag();
					else
						reader.skipTag();
				}
				reader.readNextEntry();
				Bee bee = (Bee) holder;
				bee.setHiveLocation(new Location(bee.getWorld(), x, y, z));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_FLOWER_POS, (holder, reader) -> {
			if (holder instanceof Bee) {
				reader.readNextEntry();
				int x = 0, y = 0, z = 0;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (NBT_X.equals(value))
						x = reader.readIntTag();
					else if (NBT_Y.equals(value))
						y = reader.readIntTag();
					else if (NBT_Z.equals(value))
						z = reader.readIntTag();
					else
						reader.skipTag();
				}
				reader.readNextEntry();
				Bee bee = (Bee) holder;
				bee.setFlowerLocation(new Location(bee.getWorld(), x, y, z));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_HAS_NECTAR, (holder, reader) -> {
			if (holder instanceof Bee) {
				((Bee) holder).setNectar(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_HAS_STUNG, (holder, reader) -> {
			if (holder instanceof Bee) {
				((Bee) holder).setStung(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_TICKS_SINCE_POLLINATION, (holder, reader) -> {
			if (holder instanceof Bee) {
				((Bee) holder).setTicksSincePollination(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_CANNOT_ENTER_HIVE_TICKS, (holder, reader) -> {
			if (holder instanceof Bee) {
				((Bee) holder).setTicksCannotEnterHive(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_CROPS_GROWN_SINCE_POLLINATION, (holder, reader) -> {
			if (holder instanceof Bee) {
				((Bee) holder).setCropsGrownSincePollination(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ANGER, (holder, reader) -> {
			if (holder instanceof Bee) {
				((Bee) holder).setAnger(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_HURT_BY, (holder, reader) -> {
			if (holder instanceof Bee) {
				((Bee) holder).setHurtBy(reader.readUUID());
			} else reader.skipTag();
		});
	}

	private int tickInHive;
	private int minHiveOccupationTicks;
	private int ticksSincePollination;
	private int ticksCannotEnterHive;
	private int cropsGrownSincePollination;
	private Location hive;
	private Location flower;
	private UUID hurtBy;
	
	public CoreBee(EntityType type, UUID uuid) {
		super(type, uuid);
		minHiveOccupationTicks = 600;
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_BEE_FLAGS);
		metaContainer.set(META_BEE_ANGER_TIME);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isAngry() {
		return (metaContainer.getData(META_BEE_FLAGS) & 0x02) == 0x02;
	}

	@Override
	public boolean hasStung() {
		return (metaContainer.getData(META_BEE_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public boolean hasNectar() {
		return (metaContainer.getData(META_BEE_FLAGS) & 0x08) == 0x08;
	}

	@Override
	public int getAnger() {
		return metaContainer.getData(META_BEE_ANGER_TIME);
	}

	@Override
	public int getTicksInHive() {
		return tickInHive;
	}

	@Override
	public int getHiveMinOccupationTicks() {
		return minHiveOccupationTicks;
	}

	@Override
	public void setTicksInHive(int ticks) {
		this.tickInHive = ticks;		
	}

	@Override
	public void setHiveMinOccupationTicks(int ticks) {
		this.minHiveOccupationTicks = ticks;
	}

	@Override
	public void setAngry(boolean angry) {
		MetaData<Byte> data = metaContainer.get(META_BEE_FLAGS);
		data.setData((byte) (angry ? data.getData() | 0x02 : data.getData() & 0xFD));
	}

	@Override
	public void setStung(boolean stung) {
		MetaData<Byte> data = metaContainer.get(META_BEE_FLAGS);
		data.setData((byte) (stung ? data.getData() | 0x04 : data.getData() & 0xFB));
	}

	@Override
	public void setNectar(boolean nectar) {
		MetaData<Byte> data = metaContainer.get(META_BEE_FLAGS);
		data.setData((byte) (nectar ? data.getData() | 0x08 : data.getData() & 0xF7));
	}

	@Override
	public void setAnger(int ticks) {
		if (ticks < 0)
			throw new IllegalArgumentException("Ticks must be higher than 0: " + ticks);
		metaContainer.get(META_BEE_ANGER_TIME).setData(ticks);
	}

	@Override
	public void setHiveLocation(Location location) {
		this.hive = location;
	}

	@Override
	public Location getHiveLocation() {
		return hive;
	}

	@Override
	public void setFlowerLocation(Location location) {
		this.flower = location;
	}

	@Override
	public Location getFlowerLocation() {
		return flower;
	}

	@Override
	public void setTicksSincePollination(int ticks) {
		this.ticksSincePollination = ticks;
	}

	@Override
	public int getTicksSincePollination() {
		return ticksSincePollination;
	}

	@Override
	public void setTicksCannotEnterHive(int ticks) {
		this.ticksCannotEnterHive = ticks;
	}

	@Override
	public int getTicksCannotEnterHive() {
		return ticksCannotEnterHive;
	}

	@Override
	public void setCropsGrownSincePollination(int crops) {
		this.cropsGrownSincePollination = crops;
	}

	@Override
	public int getCropsGrownSincePollination() {
		return cropsGrownSincePollination;
	}

	@Override
	public void setHurtBy(UUID uuid) {
		this.hurtBy = uuid;
	}

	@Override
	public UUID getHurtBy() {
		return hurtBy;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hive != null) {
			writer.writeCompoundTag(NBT_HIVE_POS);
			writer.writeIntTag(NBT_X, hive.getBlockX());
			writer.writeIntTag(NBT_Y, hive.getBlockY());
			writer.writeIntTag(NBT_Z, hive.getBlockZ());
			writer.writeEndTag();
		}
		if (flower != null) {
			writer.writeCompoundTag(NBT_FLOWER_POS);
			writer.writeIntTag(NBT_X, flower.getBlockX());
			writer.writeIntTag(NBT_Y, flower.getBlockY());
			writer.writeIntTag(NBT_Z, flower.getBlockZ());
			writer.writeEndTag();
		}
		if (hasNectar())
			writer.writeByteTag(NBT_HAS_NECTAR, true);
		if (hasStung())
			writer.writeByteTag(NBT_HAS_STUNG, true);
		writer.writeIntTag(NBT_TICKS_SINCE_POLLINATION, getTicksSincePollination());
		writer.writeIntTag(NBT_CANNOT_ENTER_HIVE_TICKS, getTicksCannotEnterHive());
		writer.writeIntTag(NBT_CROPS_GROWN_SINCE_POLLINATION, getCropsGrownSincePollination());
		writer.writeIntTag(NBT_ANGER, getAnger());
		if (getHurtBy() != null) 	
			writer.writeUUID(NBT_HURT_BY, getHurtBy());
	}

}
