package de.atlasmc.core.node.entity;

import org.joml.Vector3i;

import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.Bee;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;

public class CoreBee extends CoreAgeableMob implements Bee {

	protected static final int
	FLAG_IS_ANGRY = 0x02,
	FLAG_HAS_STUNG = 0x04,
	FLAG_HAS_NECTAR = 0x08;
	
	/**
	 * 0x02 - is angry<br>
	 * 0x04 - has stung<br>
	 * 0x08 - has nectar<br>
	 */
	protected static final MetaDataField<Byte>
	META_BEE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, EntityMetaTypes.BYTE);
	protected static final MetaDataField<Integer>
	META_BEE_ANGER_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, EntityMetaTypes.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;

	private int ticksSincePollination;
	private int ticksCannotEnterHive;
	private int cropsGrownSincePollination;
	private Vector3i hive;
	private Vector3i flower;
	
	public CoreBee(EntityType type) {
		super(type);
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
		return (metaContainer.getData(META_BEE_FLAGS) & FLAG_IS_ANGRY) == FLAG_IS_ANGRY;
	}

	@Override
	public boolean hasStung() {
		return (metaContainer.getData(META_BEE_FLAGS) & FLAG_HAS_STUNG) == FLAG_HAS_STUNG;
	}

	@Override
	public boolean hasNectar() {
		return (metaContainer.getData(META_BEE_FLAGS) & FLAG_HAS_NECTAR) == FLAG_HAS_NECTAR;
	}

	@Override
	public int getAngerTime() {
		return metaContainer.getData(META_BEE_ANGER_TIME);
	}

	protected void setBeeFlag(int flag, boolean set) {
		MetaData<Byte> data = metaContainer.get(META_BEE_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		metaContainer.setData(META_BEE_FLAGS, value);
	}
	
	@Override
	public void setAngry(boolean angry) {
		setBeeFlag(FLAG_IS_ANGRY, angry);
	}

	@Override
	public void setStung(boolean stung) {
		setBeeFlag(FLAG_HAS_STUNG, stung);
	}

	@Override
	public void setNectar(boolean nectar) {
		setBeeFlag(FLAG_HAS_NECTAR, nectar);
	}

	@Override
	public void setAngerTime(int ticks) {
		if (ticks < 0)
			throw new IllegalArgumentException("Ticks must be higher than 0: " + ticks);
		metaContainer.setData(META_BEE_ANGER_TIME, ticks);
	}

	@Override
	public void setHiveLocation(Vector3i location) {
		this.hive.set(location);
	}

	@Override
	public Vector3i getHiveLocationUnsafe() {
		return hive;
	}

	@Override
	public void setFlowerLocation(Vector3i location) {
		this.flower.set(location);
	}

	@Override
	public Vector3i getFlowerLocationUnsafe() {
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
	public Vector3i getHiveLocation(Vector3i vec) {
		return vec.set(hive);
	}

	@Override
	public Vector3i getFlowerLocation(Vector3i vec) {
		return vec.set(flower);
	}
	
}
