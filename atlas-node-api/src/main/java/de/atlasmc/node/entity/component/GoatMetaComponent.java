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

public class GoatMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<GoatMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(GoatMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("HasLeftHorn", GoatMetaComponent::hasLeftHorn, GoatMetaComponent::setLeftHorn, true)
					.boolField("HasRightHorn", GoatMetaComponent::hasRightHorn, GoatMetaComponent::setRightHorn, true)
					.boolField("IsScreamingGoat", GoatMetaComponent::isScreamingGoat, GoatMetaComponent::setScreamingGoat, false)
					.build();

	
	public static final MetaDataField<Boolean> 
	META_IS_SCREAMING_GOAT = new MetaDataField<>(18, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean> 
	META_HAS_LEFT_HORN = new MetaDataField<>(19, true, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean> 
	META_HAS_RIGHT_HORN = new MetaDataField<>(20, true, EntityMetaTypes.BOOLEAN);

	public GoatMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_SCREAMING_GOAT);
		container.set(META_HAS_LEFT_HORN);
		container.set(META_HAS_RIGHT_HORN);
	}

	public boolean isScreamingGoat() {
		return getHolder().getMetaContainer().getData(META_IS_SCREAMING_GOAT);
	}

	public void setScreamingGoat(boolean screaming) {
		getHolder().getMetaContainer().setData(META_IS_SCREAMING_GOAT, screaming);
	}

	public boolean hasLeftHorn() {
		return getHolder().getMetaContainer().getData(META_HAS_LEFT_HORN);
	}

	public void setLeftHorn(boolean horn) {
		getHolder().getMetaContainer().setData(META_HAS_LEFT_HORN, horn);
	}

	public boolean hasRightHorn() {
		return getHolder().getMetaContainer().getData(META_HAS_RIGHT_HORN);
	}

	public void setRightHorn(boolean horn) {
		getHolder().getMetaContainer().setData(META_HAS_RIGHT_HORN, horn);
	}
	
	@Override
	public NBTCodec<? extends GoatMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
