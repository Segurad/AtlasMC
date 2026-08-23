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

public class OcelotMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<OcelotMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(OcelotMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("Trusting", OcelotMetaComponent::isTrusting, OcelotMetaComponent::setTrusting, false)
					.build();
	
	public static final MetaDataField<Boolean>
	META_OCELOT_TRUSTING = new MetaDataField<>(18, false, EntityMetaTypes.BOOLEAN);
	
	public OcelotMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_OCELOT_TRUSTING);
	}

	public boolean isTrusting() {
		return getHolder().getMetaContainer().getData(META_OCELOT_TRUSTING);
	}

	public void setTrusting(boolean trusting) {
		getHolder().getMetaContainer().setData(META_OCELOT_TRUSTING, trusting);
	}
	@Override
	public NBTCodec<? extends OcelotMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
