package de.atlasmc.node.entity.component;

import java.util.UUID;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class TameableMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<TameableMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(TameableMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Owner", TameableMetaComponent::getOwner, TameableMetaComponent::setOwner, NBTCodecs.UUID_CODEC)
					.boolField("Sitting", TameableMetaComponent::isSitting, TameableMetaComponent::setSitting, false)
					// non standard by  atlas
					.boolField("Tame", TameableMetaComponent::isTamed, TameableMetaComponent::setTamed, false)
					.build();
	
	protected static final int
	FLAG_IS_SITTING = 0x01,
	FLAG_IS_TAMED = 0x04;
	
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is tamed
	 */
	public static final MetaDataField<Byte>
	META_TAMEABLE_FLAGS = new MetaDataField<>(18, (byte) 0, EntityMetaTypes.BYTE);
	public static final MetaDataField<UUID>
	META_OWNER = new MetaDataField<>(19, null, EntityMetaTypes.OPT_UUID);
	
	public TameableMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_TAMEABLE_FLAGS);
		container.set(META_OWNER);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}

	public boolean isSitting() {
		return (getHolder().getMetaContainer().getData(META_TAMEABLE_FLAGS) & FLAG_IS_SITTING) == FLAG_IS_SITTING;
	}
	
	protected void setTameableFlag(int flag, boolean set) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_TAMEABLE_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		container.setData(META_TAMEABLE_FLAGS, value);
	}

	public void setSitting(boolean sitting) {
		setTameableFlag(FLAG_IS_SITTING, sitting);
	}

	public boolean isTamed() {
		return (getHolder().getMetaContainer().getData(META_TAMEABLE_FLAGS) & FLAG_IS_TAMED) == FLAG_IS_TAMED;
	}

	public void setTamed(boolean tamed) {
		setTameableFlag(FLAG_IS_TAMED, tamed);
	}

	public UUID getOwner() {
		return getHolder().getMetaContainer().getData(META_OWNER);
	}

	public void setOwner(UUID owner) {
		getHolder().getMetaContainer().setData(META_OWNER, owner);
	}
	
	@Override
	public NBTCodec<? extends TameableMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
