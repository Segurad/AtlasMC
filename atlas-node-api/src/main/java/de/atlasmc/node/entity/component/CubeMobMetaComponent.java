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

public class CubeMobMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<CubeMobMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(CubeMobMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("Size", CubeMobMetaComponent::getSize, CubeMobMetaComponent::setSize, 1)
					.build();
	
	public static final MetaDataField<Integer>
	META_SLIME_SIZE = new MetaDataField<>(18, 1, EntityMetaTypes.VAR_INT);
	
	public CubeMobMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SLIME_SIZE);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	public int getSize() {
		return getHolder().getMetaContainer().getData(META_SLIME_SIZE);
	}

	public void setSize(int size) {
		getHolder().getMetaContainer().setData(META_SLIME_SIZE, size);		
	}
	
	@Override
	public NBTCodec<? extends CubeMobMetaComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
