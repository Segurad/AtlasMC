package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Sniffer extends Animal {
	
	@NotNull
	public static final NBTCodec<Sniffer>
	NBT_CODEC = NBTCodec
					.builder(Sniffer.class)
					.include(Animal.NBT_CODEC)
					.codec("SnifferState", Sniffer::getState, Sniffer::setState, EnumUtil.enumStringNBTCodec(State.class), State.IDLING) // non standard
					.build();
	
	State getState();
	
	void setState(State state);
	
	int getDropSeedAtTick();
	
	void setDropSeedAtTick(int tick);
	
	@Override
	default NBTCodec<? extends Sniffer> getNBTCodec() {
		return NBT_CODEC;
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
