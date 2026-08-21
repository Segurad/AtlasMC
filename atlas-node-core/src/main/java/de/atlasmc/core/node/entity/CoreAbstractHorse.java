package de.atlasmc.core.node.entity;

import java.util.UUID;

import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.AbstractHorse;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.inventory.AbstractHorseInventory;

public abstract class CoreAbstractHorse extends CoreAgeableMob implements AbstractHorse {

	protected static final int 
	FLAG_IS_TAME = 0x02,
	FLAG_IS_SADDLED = 0x04,
	FLAG_CAN_BRED = 0x08,
	FLAG_IS_EATING = 0x10,
	FLAG_IS_REARING = 0x20,
	FLAG_IS_MOUTH_OPEN = 0x40;
	
	/**
	 * 0x02 - Is Tame<br>
	 * 0x04 - Is saddled<br>
	 * 0x08 - can breed<br>
	 * 0x10 - Is eating<br>
	 * 0x20 - Is rearing<br>
	 * 0x40 - Is mouth open
	 */
	protected static final MetaDataField<Byte>
	META_HORSE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, EntityMetaTypes.BYTE);
	protected static final MetaDataField<UUID>
	META_HORSE_OWNER = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, null, EntityMetaTypes.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	protected AbstractHorseInventory inv;
	private int temper;
	
	public CoreAbstractHorse(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HORSE_FLAGS);
		metaContainer.set(META_HORSE_OWNER);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isSaddled() {
		return (metaContainer.getData(META_HORSE_FLAGS) & FLAG_IS_SADDLED) == FLAG_IS_SADDLED;
	}

	@Override
	public boolean canBred() {
		return (metaContainer.getData(META_HORSE_FLAGS) & FLAG_CAN_BRED) == FLAG_CAN_BRED;
	}

	@Override
	public boolean isEating() {
		return (metaContainer.getData(META_HORSE_FLAGS) & FLAG_IS_EATING) == FLAG_IS_EATING;
	}

	@Override
	public boolean isRearing() {
		return (metaContainer.getData(META_HORSE_FLAGS) & FLAG_IS_REARING) == FLAG_IS_REARING;
	}

	@Override
	public boolean isMouthOpen() {
		return (metaContainer.getData(META_HORSE_FLAGS) & FLAG_IS_MOUTH_OPEN) == FLAG_IS_MOUTH_OPEN;
	}

	@Override
	public UUID getOwner() {
		return metaContainer.getData(META_HORSE_OWNER);
	}

	@Override
	public boolean isTamed() {
		return (metaContainer.getData(META_HORSE_FLAGS) & FLAG_IS_TAME) == FLAG_IS_TAME;
	}
	
	protected void setHorseFlag(int flag, boolean set) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		metaContainer.setData(META_HORSE_FLAGS, value);
	}

	@Override
	public void setTamed(boolean tamed) {
		setHorseFlag(FLAG_IS_TAME, tamed);
	}

	@Override
	public void setSaddled(boolean saddled) {
		setHorseFlag(FLAG_IS_SADDLED, saddled);
	}

	@Override
	public void setCanBred(boolean breed) {
		setHorseFlag(FLAG_CAN_BRED, breed);
	}

	@Override
	public void setEating(boolean eating) {
		setHorseFlag(FLAG_IS_EATING, eating);
	}

	@Override
	public void setRearing(boolean rearing) {
		setHorseFlag(FLAG_IS_REARING, rearing);
	}

	@Override
	public void setMouthOpen(boolean open) {
		setHorseFlag(FLAG_IS_MOUTH_OPEN, open);
	}

	@Override
	public void setOwner(UUID owner) {
		metaContainer.setData(META_HORSE_OWNER, owner);
	}

	@Override
	public AbstractHorseInventory getInventory() {
		if (inv == null)
			inv = createInventory();
		return inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}
	
	protected abstract AbstractHorseInventory createInventory();

	@Override
	public int getTemper() {
		return temper;
	}

	@Override
	public void setTemper(int temper) {
		this.temper = temper;
	}

}
