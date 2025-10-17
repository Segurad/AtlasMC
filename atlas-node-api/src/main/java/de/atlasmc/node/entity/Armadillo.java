package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Armadillo extends Animal {
	
	public static final NBTCodec<Armadillo>
	NBT_HANDLER = NBTCodec
					.builder(Armadillo.class)
					.include(Animal.NBT_HANDLER)
					.intField("scute_time", Armadillo::getScuteTime, Armadillo::setScuteTime, -1)
					.enumStringField("state", Armadillo::getState, Armadillo::setState, ArmadilloState.class, ArmadilloState.IDLE)
					.build();
	
	int getScuteTime();
	
	void setScuteTime(int time);
	
	ArmadilloState getState();
	
	void setState(ArmadilloState state);
	
	@Override
	default NBTCodec<? extends Armadillo> getNBTCodec() {
		return NBT_HANDLER;
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
