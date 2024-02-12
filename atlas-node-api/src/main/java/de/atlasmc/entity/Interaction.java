package de.atlasmc.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface Interaction extends Entity {
	
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
	
	public static class PreviousInteraction extends AbstractNBTBase {
		
		protected static final NBTFieldContainer<PreviousInteraction> NBT_FIELDS;
		
		protected static final CharKey
		NBT_PLAYER = CharKey.literal("player"),
		NBT_TIMESTAMP = CharKey.literal("timestamp");
		
		static {
			NBT_FIELDS = new NBTFieldContainer<>();
			NBT_FIELDS.setField(NBT_PLAYER, (holder, reader) -> {
				holder.uuid = reader.readUUID();
			});
			NBT_FIELDS.setField(NBT_TIMESTAMP, (holder, reader) -> {
				holder.timestamp = reader.readLongTag();
			});
		}
		
		private UUID uuid;
		private long timestamp;
		
		public PreviousInteraction(NBTReader reader) throws IOException {
			fromNBT(reader);
		}
		
		public PreviousInteraction(UUID uuid, long timestamp) {
			this.uuid = uuid;
			this.timestamp = timestamp;
		}
		
		public long getTimestamp() {
			return timestamp;
		}
		
		public UUID getUUID() {
			return uuid;
		}

		@Override
		public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
			writer.writeUUID(NBT_PLAYER, uuid);
			writer.writeLongTag(NBT_TIMESTAMP, timestamp);
		}

		@Override
		protected NBTFieldContainer<? extends PreviousInteraction> getFieldContainerRoot() {
			return NBT_FIELDS;
		}
		
	}

}
