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
import de.atlasmc.util.enums.EnumUtil;

public class DragonPhaseMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<DragonPhaseMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(DragonPhaseMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("DragonPhase", DragonPhaseMetaComponent::getPhase, DragonPhaseMetaComponent::setPhase, EnumUtil.enumIntNBTCodec(DragonPhase.class), DragonPhase.HOVERING)
					.build();
	
	protected static final MetaDataField<Integer>
	META_DRAGON_PHASE = new MetaDataField<>(16, DragonPhase.HOVERING.getID(), EntityMetaTypes.VAR_INT);
	
	public DragonPhaseMetaComponent(ComponentType type) {
		super(type);
	}

	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DRAGON_PHASE);
	}
	
	public DragonPhase getPhase() {
		return EnumUtil.getByID(DragonPhase.class, getHolder().getMetaContainer().getData(META_DRAGON_PHASE));
	}

	public void setPhase(DragonPhase phase) {
		getHolder().getMetaContainer().setData(META_DRAGON_PHASE, phase.getID());
	}
	
	@Override
	public NBTCodec<? extends DragonPhaseMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum DragonPhase implements IDHolder {
		
		CIRCLING,
		STRAFING,
		FLYING_TO_PORTAL,
		LANDING,
		TAKING_OFF,
		LANDED_BREATH_ATTACK,
		LANDED_BREATH_ATTACK_LOOKING,
		LANDED_BREATH_ATTACK_BEGINNING,
		CHARGING,
		FLYING_TO_DIE,
		HOVERING;

		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
