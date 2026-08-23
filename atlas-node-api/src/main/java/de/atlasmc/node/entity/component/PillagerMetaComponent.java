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

public class PillagerMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<PillagerMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(PillagerMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("IsCharging", PillagerMetaComponent::isCharging, PillagerMetaComponent::setCharging, false) // non standard
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_CHARGING = new MetaDataField<>(17, false, EntityMetaTypes.BOOLEAN);
	
	public PillagerMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_CHARGING);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public boolean isCharging() {
		return getHolder().getMetaContainer().getData(META_IS_CHARGING);
	}

	public void setCharging(boolean charging) {
		getHolder().getMetaContainer().setData(META_IS_CHARGING, charging);
	}
	
	@Override
	public NBTCodec<? extends PillagerMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
