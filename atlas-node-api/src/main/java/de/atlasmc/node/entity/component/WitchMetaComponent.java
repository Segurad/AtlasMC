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

public class WitchMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<WitchMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(WitchMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("IsDrinkingPotion", WitchMetaComponent::isDrinkingPotion, WitchMetaComponent::setDrinkingPotion, false) // non standard
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_DRINKING_POTION = new MetaDataField<>(17, false, EntityMetaTypes.BOOLEAN);
	
	public WitchMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_DRINKING_POTION);
	}

	public boolean isDrinkingPotion() {
		return getHolder().getMetaContainer().getData(META_IS_DRINKING_POTION);
	}

	public void setDrinkingPotion(boolean drinking) {
		getHolder().getMetaContainer().setData(META_IS_DRINKING_POTION, drinking);		
	}
	
	@Override
	public NBTCodec<? extends WitchMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
