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

public class RaiderMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<RaiderMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(RaiderMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("IsCelebrating", RaiderMetaComponent::isCelebrating, RaiderMetaComponent::setCelebrating, false) // non standard
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_CELEBRATING = new MetaDataField<>(16, false, EntityMetaTypes.BOOLEAN);
	
	public RaiderMetaComponent(ComponentType type) {
		super(type);
	}

	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_CELEBRATING);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	public boolean isCelebrating() {
		return getHolder().getMetaContainer().getData(META_IS_CELEBRATING);
	}

	public void setCelebrating(boolean celebrating) {
		getHolder().getMetaContainer().setData(META_IS_CELEBRATING, celebrating);
	}
	
	@Override
	public NBTCodec<? extends RaiderMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
