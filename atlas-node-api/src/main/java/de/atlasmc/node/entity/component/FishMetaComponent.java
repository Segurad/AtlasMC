package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class FishMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<FishMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(FishMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("FromBucket", FishMetaComponent::isFromBucket, FishMetaComponent::setFromBucket, false)
					.build();
	
	public static final MetaDataField<Boolean>
	META_FROM_BUCKET = new MetaDataField<>(16, false, EntityMetaTypes.BOOLEAN);
	
	public FishMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_FROM_BUCKET);
	}

	public boolean isFromBucket() {
		return getHolder().getMetaContainer().getData(META_FROM_BUCKET);
	}

	public void setFromBucket(boolean from) {
		getHolder().getMetaContainer().setData(META_FROM_BUCKET, from);
	}
	
	@Override
	public NBTCodec<? extends FishMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
