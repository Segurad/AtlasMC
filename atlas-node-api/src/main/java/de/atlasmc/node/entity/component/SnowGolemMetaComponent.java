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

public class SnowGolemMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<SnowGolemMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(SnowGolemMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("Pumpkin", SnowGolemMetaComponent::hasHat, SnowGolemMetaComponent::setHat, true)
					.build();
	
	public static final int
	FLAG_HAS_HAT = 0x10;
	
	public static final MetaDataField<Byte>
	META_SNOW_GOLEM_FLAGS = new MetaDataField<>(16, (byte) 0x10, EntityMetaTypes.BYTE);
	
	public SnowGolemMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SNOW_GOLEM_FLAGS);
	}
	
	public boolean hasHat() {
		return (getHolder().getMetaContainer().getData(META_SNOW_GOLEM_FLAGS) & FLAG_HAS_HAT) == FLAG_HAS_HAT;
	}
	
	public void setHat(boolean has) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_SNOW_GOLEM_FLAGS);
		var value = (byte) (has ? data.getData() | FLAG_HAS_HAT : data.getData() & ~FLAG_HAS_HAT);
		container.setData(META_SNOW_GOLEM_FLAGS, value);
	}

	@Override
	public NBTCodec<? extends SnowGolemMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
