package de.atlasmc.node.entity.component;

import java.util.UUID;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class InteractionMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<InteractionMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(InteractionMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.floatField("width", InteractionMetaComponent::getWidth, InteractionMetaComponent::setWidth, 1)
					.floatField("height", InteractionMetaComponent::getHeight, InteractionMetaComponent::setHeight, 1)
					.boolField("response", InteractionMetaComponent::isResponsive, InteractionMetaComponent::setResponsive, false)
					.codec("attack", InteractionMetaComponent::getLastAttack, InteractionMetaComponent::setLastAttack, PreviousInteraction.NBT_CODEC)
					.codec("interaction", InteractionMetaComponent::getLastInteraction, InteractionMetaComponent::setLastInteraction, PreviousInteraction.NBT_CODEC)
					.build();
	
	public static final MetaDataField<Float> 
	META_WIDTH = new MetaDataField<>(8, 1f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Float> 
	META_HEIGHT = new MetaDataField<>(9, 1f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Boolean> 
	META_RESPONSIVE = new MetaDataField<Boolean>(10, false, EntityMetaTypes.BOOLEAN);
	
	private PreviousInteraction lastAttack;
	private PreviousInteraction lastInteraction;
	
	public InteractionMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_WIDTH);
		container.set(META_HEIGHT);
		container.set(META_RESPONSIVE);
	}
	
	public void setWidth(float width) {
		getHolder().getMetaContainer().setData(META_WIDTH, width);
	}

	public float getWidth() {
		return getHolder().getMetaContainer().getData(META_WIDTH);
	}

	public void setHeight(float height) {
		getHolder().getMetaContainer().setData(META_HEIGHT, height);
	}

	public float getHeight() {
		return getHolder().getMetaContainer().getData(META_HEIGHT);
	}

	public boolean isResponsive() {
		return getHolder().getMetaContainer().getData(META_RESPONSIVE);
	}

	public void setResponsive(boolean responsive) {
		getHolder().getMetaContainer().setData(META_RESPONSIVE, responsive);
	}

	public PreviousInteraction getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(PreviousInteraction interaction) {
		this.lastAttack = interaction;
	}

	public PreviousInteraction getLastInteraction() {
		return lastInteraction;
	}

	public void setLastInteraction(PreviousInteraction interaction) {
		this.lastInteraction = interaction;
	}
	
	@Override
	public NBTCodec<? extends InteractionMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static class PreviousInteraction implements NBTSerializable {
		
		@NotNull
		public static final NBTCodec<PreviousInteraction>
		NBT_CODEC = NBTCodec
						.builder(PreviousInteraction.class)
						.defaultConstructor(PreviousInteraction::new)
						.codec("player", PreviousInteraction::getUUID, PreviousInteraction::setUUID, NBTCodecs.UUID_CODEC)
						.longField("timestamp", PreviousInteraction::getTimestamp, PreviousInteraction::setTimestamp)
						.build();
		
		private UUID uuid;
		private long timestamp;
		
		private PreviousInteraction() {
			// empty constructor for deserialisation
		}
		
		public PreviousInteraction(UUID uuid, long timestamp) {
			this.uuid = uuid;
			this.timestamp = timestamp;
		}
		
		public long getTimestamp() {
			return timestamp;
		}
		
		private void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
		public UUID getUUID() {
			return uuid;
		}
		
		private void setUUID(UUID uuid) {
			this.uuid = uuid;
		}
		
		@Override
		public NBTCodec<? extends PreviousInteraction> getNBTCodec() {
			return NBT_CODEC;
		}
		
	}
	
}
