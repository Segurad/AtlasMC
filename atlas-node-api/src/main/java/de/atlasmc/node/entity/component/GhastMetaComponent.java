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

public class GhastMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<GhastMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(GhastMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("Attacking", GhastMetaComponent::isAttacking, GhastMetaComponent::setAttacking, false) // non standard
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_ATTACKING = new MetaDataField<>(16, false, EntityMetaTypes.BOOLEAN);

	public GhastMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_ATTACKING);
	}

	public boolean isAttacking() {
		return getHolder().getMetaContainer().getData(META_IS_ATTACKING);
	}

	public void setAttacking(boolean attacking) {
		getHolder().getMetaContainer().setData(META_IS_ATTACKING, attacking);		
	}
	
	@Override
	public NBTCodec<? extends GhastMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
