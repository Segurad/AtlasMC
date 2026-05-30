package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.annotation.NotNull;

public interface Interaction extends Entity {
	
	@NotNull
	public static final NBTCodec<Interaction>
	NBT_CODEC = NBTCodec
					.builder(Interaction.class)
					.include(Entity.NBT_CODEC)
					.floatField("width", Interaction::getWidth, Interaction::setWidth, 1)
					.floatField("height", Interaction::getHeight, Interaction::setHeight, 1)
					.boolField("response", Interaction::isResponsive, Interaction::setResponsive, false)
					.codec("attack", Interaction::getLastAttack, Interaction::setLastAttack, PreviousInteraction.NBT_CODEC)
					.codec("interaction", Interaction::getLastInteraction, Interaction::setLastInteraction, PreviousInteraction.NBT_CODEC)
					.build();
	
	void setWidth(float width);
	
	float getWidth();
	
	void setHeight(float height);
	
	float getHeight();
	
	boolean isResponsive();
	
	void setResponsive(boolean responsive);
	
	PreviousInteraction getLastAttack();
	
	void setLastAttack(PreviousInteraction interaction);
	
	PreviousInteraction getLastInteraction();
	
	void setLastInteraction(PreviousInteraction interaction);
	
	@Override
	default NBTCodec<? extends Interaction> getNBTCodec() {
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
