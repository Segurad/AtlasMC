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

public class BatMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<BatMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(BatMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("BatFlags", BatMetaComponent::isHanging, BatMetaComponent::setHanging, false)
					.build();
	
	public static final int FLAG_IS_HANGING = 0x01;
	
	public static final MetaDataField<Byte>
	META_BAT_FLAGS = new MetaDataField<>(16, (byte) 0, EntityMetaTypes.BYTE);
	
	public BatMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_BAT_FLAGS);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public boolean isHanging() {
		return (getHolder().getMetaContainer().getData(META_BAT_FLAGS) & FLAG_IS_HANGING) == FLAG_IS_HANGING;
	}
	
	public void setHanging(boolean hanging) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_BAT_FLAGS);
		container.setData(META_BAT_FLAGS, (byte) (hanging ? data.getData() | FLAG_IS_HANGING : data.getData() & ~FLAG_IS_HANGING));
	}

	@Override
	public NBTCodec<? extends BatMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
