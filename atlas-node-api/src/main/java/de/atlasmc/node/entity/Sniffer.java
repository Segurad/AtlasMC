package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Sniffer extends Animal {
	
	public static final NBTSerializationHandler<Sniffer>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Sniffer.class)
					.include(Animal.NBT_HANDLER)
					.enumStringField("SnifferState", Sniffer::getState, Sniffer::setState, State.class, State.IDLING) // non standard
					.build();
	
	State getState();
	
	void setState(State state);
	
	int getDropSeedAtTick();
	
	void setDropSeedAtTick(int tick);
	
	@Override
	default NBTSerializationHandler<? extends Sniffer> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum State implements IDHolder, EnumName {
		
		IDLING,
		FEELING_HAPPY,
		SCENTING,
		SNIFFING,
		SEARCHING,
		DIGGING,
		RISING;
		
		@Override
		public String getName() {
			return name();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
