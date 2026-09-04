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

public class AllayMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<AllayMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(AllayMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					// non standard by atlas
					.boolField("IsDancing", AllayMetaComponent::isDancing, AllayMetaComponent::setDancing, false)
					.boolField("CanDuplicate", AllayMetaComponent::canDuplicate, AllayMetaComponent::setCanDuplicate, false) 
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_DANCING = new MetaDataField<>(16, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean>
	META_CAN_DUPLICATE = new MetaDataField<>(17, true, EntityMetaTypes.BOOLEAN);
	
	public AllayMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_DANCING);
		container.set(META_CAN_DUPLICATE);
	}
	
	public boolean canDuplicate() {
		return getHolder().getMetaContainer().getData(META_CAN_DUPLICATE);
	}
	
	public void setCanDuplicate(boolean value) {
		getHolder().getMetaContainer().setData(META_CAN_DUPLICATE, value);
	}
	
	public boolean isDancing() {
		return getHolder().getMetaContainer().getData(META_IS_DANCING);
	}
	
	public void setDancing(boolean dancing) {
		getHolder().getMetaContainer().setData(META_IS_DANCING, dancing);
	}
	
	@Override
	public NBTCodec<? extends AllayMetaComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
