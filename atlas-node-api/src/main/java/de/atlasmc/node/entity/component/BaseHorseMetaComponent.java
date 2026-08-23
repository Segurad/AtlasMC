package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class BaseHorseMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<BaseHorseMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(BaseHorseMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("Bred", BaseHorseMetaComponent::canBred, BaseHorseMetaComponent::setCanBred, false)
					.boolField("EatingHaystack", BaseHorseMetaComponent::isEating, BaseHorseMetaComponent::setEating, false)
					.boolField("Tame", BaseHorseMetaComponent::isTamed, BaseHorseMetaComponent::setTamed, false)
					// non standard by atlas
					.boolField("Rearing", BaseHorseMetaComponent::isRearing, BaseHorseMetaComponent::setRearing, false)
					.boolField("MouthOpen", BaseHorseMetaComponent::isMouthOpen, BaseHorseMetaComponent::setMouthOpen, false)
					.build();
	
	public static final int 
	FLAG_IS_TAME = 0x02,
	FLAG_IS_SADDLED = 0x04,
	FLAG_CAN_BRED = 0x08,
	FLAG_IS_EATING = 0x10,
	FLAG_IS_REARING = 0x20,
	FLAG_IS_MOUTH_OPEN = 0x40;
	
	public static final MetaDataField<Byte>
	META_HORSE_FLAGS = new MetaDataField<>(18, (byte) 0, EntityMetaTypes.BYTE);
	
	public BaseHorseMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_HORSE_FLAGS);
	}	

	public boolean isSaddled() {
		return (getHolder().getMetaContainer().getData(META_HORSE_FLAGS) & FLAG_IS_SADDLED) == FLAG_IS_SADDLED;
	}

	public boolean canBred() {
		return (getHolder().getMetaContainer().getData(META_HORSE_FLAGS) & FLAG_CAN_BRED) == FLAG_CAN_BRED;
	}

	public boolean isEating() {
		return (getHolder().getMetaContainer().getData(META_HORSE_FLAGS) & FLAG_IS_EATING) == FLAG_IS_EATING;
	}

	public boolean isRearing() {
		return (getHolder().getMetaContainer().getData(META_HORSE_FLAGS) & FLAG_IS_REARING) == FLAG_IS_REARING;
	}

	public boolean isMouthOpen() {
		return (getHolder().getMetaContainer().getData(META_HORSE_FLAGS) & FLAG_IS_MOUTH_OPEN) == FLAG_IS_MOUTH_OPEN;
	}

	public boolean isTamed() {
		return (getHolder().getMetaContainer().getData(META_HORSE_FLAGS) & FLAG_IS_TAME) == FLAG_IS_TAME;
	}
	
	protected void setHorseFlag(int flag, boolean set) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_HORSE_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		container.setData(META_HORSE_FLAGS, value);
	}

	public void setTamed(boolean tamed) {
		setHorseFlag(FLAG_IS_TAME, tamed);
	}

	public void setSaddled(boolean saddled) {
		setHorseFlag(FLAG_IS_SADDLED, saddled);
	}

	public void setCanBred(boolean breed) {
		setHorseFlag(FLAG_CAN_BRED, breed);
	}

	public void setEating(boolean eating) {
		setHorseFlag(FLAG_IS_EATING, eating);
	}

	public void setRearing(boolean rearing) {
		setHorseFlag(FLAG_IS_REARING, rearing);
	}

	public void setMouthOpen(boolean open) {
		setHorseFlag(FLAG_IS_MOUTH_OPEN, open);
	}
	
	@Override
	public NBTCodec<? extends BaseHorseMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
