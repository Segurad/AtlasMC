package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Sniffer extends Animal {
	
	public static final NBTSerializationHandler<Sniffer>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Sniffer.class)
					.include(Animal.NBT_HANDLER)
					.enumStringField("SnifferState", Sniffer::getState, Sniffer::setState, State::getByName, State.IDLING) // non standard
					.build();
	
	State getState();
	
	void setState(State state);
	
	int getDropSeedAtTick();
	
	void setDropSeedAtTick(int tick);
	
	@Override
	default NBTSerializationHandler<? extends Sniffer> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum State implements AtlasEnum {
		IDLING,
		FEELING_HAPPY,
		SCENTING,
		SNIFFING,
		SEARCHING,
		DIGGING,
		RISING;
		
		private static List<State> VALUES;
		
		@Override
		public String getName() {
			return name();
		}
		
		public static State getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (State value : getValues()) {
				if (value.getName().equals(name))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + name);
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static State getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<State> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}

		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
