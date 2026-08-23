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

public class ChestedHorseMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<ChestedHorseMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(ChestedHorseMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("ChestedHorse", ChestedHorseMetaComponent::hasChest, ChestedHorseMetaComponent::setChest, false)
					.build();
	
	protected static final MetaDataField<Boolean>
	META_HORSE_HAS_CHEST = new MetaDataField<>(19, false, EntityMetaTypes.BOOLEAN);
	
	public ChestedHorseMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_HORSE_HAS_CHEST);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public boolean hasChest() {
		return getHolder().getMetaContainer().getData(META_HORSE_HAS_CHEST);
	}

	public void setChest(boolean chest) {
		getHolder().getMetaContainer().setData(META_HORSE_HAS_CHEST, chest);
	}
	
	@Override
	public NBTCodec<? extends ChestedHorseMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
