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

public class BabyMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<BabyMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(BabyMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("IsBaby", BabyMetaComponent::isBaby, BabyMetaComponent::setBaby, false)
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_BABY = new MetaDataField<>(16, false, EntityMetaTypes.BOOLEAN);
	
	public BabyMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_BABY);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public boolean isBaby() {
		return getHolder().getMetaContainer().getData(META_IS_BABY);
	}

	public void setBaby(boolean baby) {
		getHolder().getMetaContainer().setData(META_IS_BABY, baby);
	}
	
	@Override
	public NBTCodec<? extends BabyMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
