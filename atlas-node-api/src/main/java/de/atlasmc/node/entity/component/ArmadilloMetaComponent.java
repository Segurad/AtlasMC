package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class ArmadilloMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	public static final MetaDataField<ArmadilloState>
	META_ARMADILLO_STATE = new MetaDataField<>(18, ArmadilloState.IDLE, EntityMetaTypes.ARMADILLO_STATE);
	
	@NotNull
	public static final NBTCodec<ArmadilloMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(ArmadilloMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("state", ArmadilloMetaComponent::getState, ArmadilloMetaComponent::setState, EnumUtil.enumStringNBTCodec(ArmadilloState.class), ArmadilloState.IDLE)
					.build();
	
	public ArmadilloMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_ARMADILLO_STATE);
	}

	public ArmadilloState getState() {
		return getHolder().getMetaContainer().getData(META_ARMADILLO_STATE);
	}

	public void setState(ArmadilloState state) {
		getHolder().getMetaContainer().setData(META_ARMADILLO_STATE, state);
	}
	
	@Override
	public NBTCodec<? extends ArmadilloMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum ArmadilloState implements EnumName, IDHolder {
		
		IDLE,
		ROLLING,
		SCARED,
		UNROLLING;
		
		private String name;
		
		private ArmadilloState() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}


}
