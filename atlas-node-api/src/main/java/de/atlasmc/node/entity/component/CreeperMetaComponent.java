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

public class CreeperMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<CreeperMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(CreeperMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("ignited", CreeperMetaComponent::isIgnited, CreeperMetaComponent::setIgnited, false)
					.boolField("powered", CreeperMetaComponent::isChared, CreeperMetaComponent::setChared, false)
					.boolField("Fusing", CreeperMetaComponent::isFusing, CreeperMetaComponent::setFusing, false) // non standard
					.build();
	
	public static final MetaDataField<Integer>
	META_CREEPER_STATE = new MetaDataField<>(16, -1, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Boolean>
	META_IS_CHARGED = new MetaDataField<>(17, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean>
	META_IS_IGNITED = new MetaDataField<>(18, false, EntityMetaTypes.BOOLEAN);
	
	public CreeperMetaComponent(ComponentType type) {
		super(type);
	}

	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_CREEPER_STATE);
		container.set(META_IS_CHARGED);
		container.set(META_IS_IGNITED);
	}

	public boolean isChared() {
		return getHolder().getMetaContainer().getData(META_IS_CHARGED);
	}

	public boolean isIgnited() {
		return getHolder().getMetaContainer().getData(META_IS_IGNITED);
	}

	public void setChared(boolean charged) {
		getHolder().getMetaContainer().setData(META_IS_CHARGED, charged);
	}

	public void setIgnited(boolean ignited) {
		getHolder().getMetaContainer().setData(META_IS_IGNITED, ignited);
	}

	public boolean isFusing() {
		return getHolder().getMetaContainer().getData(META_CREEPER_STATE) == 1;
	}

	/**
	 * Returns whether or not the {@link Creeper} is displayed as fusing
	 * @return true if fusing
	 */
	public void setFusing(boolean fuzing) {
		getHolder().getMetaContainer().setData(META_CREEPER_STATE, fuzing ? 1 : -1);
	}
	
	@Override
	public NBTCodec<? extends CreeperMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
