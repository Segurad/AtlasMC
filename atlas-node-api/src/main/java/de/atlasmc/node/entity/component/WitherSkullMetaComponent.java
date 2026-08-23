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

public class WitherSkullMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<WitherSkullMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(WitherSkullMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("dangerous", WitherSkullMetaComponent::isDangerous, WitherSkullMetaComponent::setDangerous, false)
					.build();
	
	public static final MetaDataField<Boolean>
	META_DANGEROUS = new MetaDataField<>(8, false, EntityMetaTypes.BOOLEAN);
	
	public WitherSkullMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DANGEROUS);
	}

	public boolean isDangerous() {
		return getHolder().getMetaContainer().getData(META_DANGEROUS);
	}

	public void setDangerous(boolean charged) {
		getHolder().getMetaContainer().setData(META_DANGEROUS, charged);		
	}
	
	@Override
	public NBTCodec<? extends WitherSkullMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
