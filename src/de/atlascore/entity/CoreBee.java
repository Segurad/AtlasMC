package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Bee;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreBee extends CoreAgeableMob implements Bee {

	/**
	 * 0x02 - is angry<br>
	 * 0x04 - has stung<br>
	 * 0x08 - has nectar<br>
	 */
	protected static final MetaDataField<Byte>
	META_BEE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Integer>
	META_BEE_ANGER_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	protected static final String
	NBT_HIVE_POS = "HivePos",
	NBT_FLOWER_POS = "FlowerPos",
	NBT_HAS_NECTAR = "HasNextar",
	NBT_HAS_STUNG = "HasStung",
	NBT_TICKS_SINCE_POLLINATION = "TicksSincePollination",
	NBT_CANNOT_ENTER_HIVE_TICKS = "CannotEnterHiveTicks",
	NBT_CROPS_GROWN_SINCE_POLLINATION = "CropsGrownSincePollination",
	NBT_ANGER = "Anger",
	NBT_HURT_BY = "HurtBy";

	static {
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
	}

	private int tickInHive, minHiveOccupationTicks;
	
	public CoreBee(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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

}
