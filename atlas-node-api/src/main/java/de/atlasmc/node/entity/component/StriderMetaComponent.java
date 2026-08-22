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

public class StriderMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<StriderMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(StriderMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("BoostTime", StriderMetaComponent::getBoostTime, StriderMetaComponent::setBoostTime, 0)
					.boolField("IsShaking", StriderMetaComponent::isShaking, StriderMetaComponent::setShaking, false)
					.build();
	
	public static final MetaDataField<Integer>
	META_BOOST_TIME = new MetaDataField<>(18, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Boolean>
	META_IS_SHAKING = new MetaDataField<>(19, false, EntityMetaTypes.BOOLEAN);
	
	public StriderMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_BOOST_TIME);
		container.set(META_IS_SHAKING);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}

	public int getBoostTime() {
		return getHolder().getMetaContainer().getData(META_BOOST_TIME);
	}

	public boolean isShaking() {
		return getHolder().getMetaContainer().getData(META_IS_SHAKING);
	}

	public void setBoostTime(int time) {
		getHolder().getMetaContainer().setData(META_BOOST_TIME, time);		
	}

	public void setShaking(boolean shaking) {
		getHolder().getMetaContainer().setData(META_IS_SHAKING, shaking);		
	}
	
	@Override
	public NBTCodec<? extends StriderMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
