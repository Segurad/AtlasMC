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

public class PolarBearMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<PolarBearMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(PolarBearMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					// non standard by atlas
					.boolField("StandingUp", PolarBearMetaComponent::isStandingUp, PolarBearMetaComponent::setStandingUp, false)
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_STANDING_UP = new MetaDataField<>(18, false, EntityMetaTypes.BOOLEAN);
	
	public PolarBearMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_STANDING_UP);
	}

	public boolean isStandingUp() {
		return getHolder().getMetaContainer().getData(META_IS_STANDING_UP);
	}

	public void setStandingUp(boolean standing) {
		getHolder().getMetaContainer().setData(META_IS_STANDING_UP, standing);
	}

	@Override
	public NBTCodec<? extends PolarBearMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
