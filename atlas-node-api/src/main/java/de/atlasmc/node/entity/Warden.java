package de.atlasmc.node.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Warden extends Monster {
	
	public static final NBTCodec<Warden>
	NBT_HANDLER = NBTCodec
					.builder(Warden.class)
					.include(Monster.NBT_HANDLER)
					.beginComponent("anger")
					.typeList("suspects", Warden::hasSuspects, Warden::getSuspects, Suspect.NBT_HANDLER)
					.intField("angerLevel", Warden::getAnger, Warden::setAnger, 0) // non standard
					.endComponent()
					.build();
	
	boolean hasSuspects();
	
	@NotNull
	List<Suspect> getSuspects();
	
	int getAnger();
	
	void setAnger(int anger);
	
	@Override
	default NBTCodec<? extends Warden> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static class Suspect implements NBTSerializable {
		
		public static final NBTCodec<Suspect>
		NBT_HANDLER = NBTCodec
						.builder(Suspect.class)
						.defaultConstructor(Suspect::new)
						.intField("anger", Suspect::getAnger, Suspect::setAnger)
						.uuid("uuid", Suspect::getUUID, Suspect::setUUID)
						.build();
		
		private UUID uuid;
		private int anger;
		
		private Suspect() {
			// nbt constructor
		}
		
		public Suspect(UUID uuid, int anger) {
			this.uuid = uuid;
			this.anger = anger;
		}
		
		public int getAnger() {
			return anger;
		}
		
		public void setAnger(int anger) {
			this.anger = anger;
		}
		
		public UUID getUUID() {
			return uuid;
		}
		
		private void setUUID(UUID uuid) {
			this.uuid = uuid;
		}
		
		@Override
		public NBTCodec<? extends NBTSerializable> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}

}
