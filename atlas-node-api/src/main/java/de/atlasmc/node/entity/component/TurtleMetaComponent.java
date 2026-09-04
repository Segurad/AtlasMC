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

public class TurtleMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<TurtleMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(TurtleMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("has_egg", TurtleMetaComponent::hasEgg, TurtleMetaComponent::setEgg, false)
					// non standard by atlas
					.boolField("laying_egg", TurtleMetaComponent::hasEgg, TurtleMetaComponent::setEgg, false)
					.build();
	
	public static final MetaDataField<Boolean>
	META_HAS_EGG = new MetaDataField<>(18, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean>
	META_IS_LAYING_EGG = new MetaDataField<>(19, false, EntityMetaTypes.BOOLEAN);
	
	public TurtleMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_HAS_EGG);
		container.set(META_IS_LAYING_EGG);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	public boolean hasEgg() {
		return getHolder().getMetaContainer().getData(META_HAS_EGG);
	}

	public boolean isLayingEgg() {
		return getHolder().getMetaContainer().getData(META_IS_LAYING_EGG);
	}

	public void setEgg(boolean egg) {
		getHolder().getMetaContainer().setData(META_HAS_EGG, egg);
	}

	public void setLayingEgg(boolean laying) {
		getHolder().getMetaContainer().setData(META_IS_LAYING_EGG, laying);		
	}
	
	@Override
	public NBTCodec<? extends TurtleMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
