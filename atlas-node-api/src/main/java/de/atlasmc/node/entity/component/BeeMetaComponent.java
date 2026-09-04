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

public class BeeMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<BeeMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(BeeMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("HasNectar", BeeMetaComponent::hasNectar, BeeMetaComponent::setNectar, false)
					.boolField("HasStung", BeeMetaComponent::hasStung, BeeMetaComponent::setStung, false)
					.longField("AngerTime", BeeMetaComponent::getAngerTime, BeeMetaComponent::setAngerTime, -1L)
					// non standard by atlas
					.boolField("IsAngry", BeeMetaComponent::isAngry, BeeMetaComponent::setAngry, false)
					.build();
	
	public static final int
	FLAG_IS_ANGRY = 0x02,
	FLAG_HAS_STUNG = 0x04,
	FLAG_HAS_NECTAR = 0x08;
	
	public static final MetaDataField<Byte>
	META_BEE_FLAGS = new MetaDataField<>(18, (byte) 0, EntityMetaTypes.BYTE);
	public static final MetaDataField<Long>
	META_BEE_ANGER_TIME = new MetaDataField<>(19, -1L, EntityMetaTypes.VAR_LONG);
	
	public BeeMetaComponent(ComponentType type) {
		super(type);
	}

	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_BEE_FLAGS);
		container.set(META_BEE_ANGER_TIME);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	public boolean isAngry() {
		return (getHolder().getMetaContainer().getData(META_BEE_FLAGS) & FLAG_IS_ANGRY) == FLAG_IS_ANGRY;
	}

	public boolean hasStung() {
		return (getHolder().getMetaContainer().getData(META_BEE_FLAGS) & FLAG_HAS_STUNG) == FLAG_HAS_STUNG;
	}

	public boolean hasNectar() {
		return (getHolder().getMetaContainer().getData(META_BEE_FLAGS) & FLAG_HAS_NECTAR) == FLAG_HAS_NECTAR;
	}

	public long getAngerTime() {
		return getHolder().getMetaContainer().getData(META_BEE_ANGER_TIME);
	}

	protected void setBeeFlag(int flag, boolean set) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_BEE_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		container.setData(META_BEE_FLAGS, value);
	}
	
	public void setAngry(boolean angry) {
		setBeeFlag(FLAG_IS_ANGRY, angry);
	}

	public void setStung(boolean stung) {
		setBeeFlag(FLAG_HAS_STUNG, stung);
	}

	public void setNectar(boolean nectar) {
		setBeeFlag(FLAG_HAS_NECTAR, nectar);
	}

	public void setAngerTime(long ticks) {
		if (ticks < 0)
			throw new IllegalArgumentException("Ticks must be higher than 0: " + ticks);
		getHolder().getMetaContainer().setData(META_BEE_ANGER_TIME, ticks);
	}
	
	@Override
	public NBTCodec<? extends BeeMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
