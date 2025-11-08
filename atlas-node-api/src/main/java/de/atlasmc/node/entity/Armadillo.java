package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Armadillo extends Animal {
	
	public static final NBTCodec<Armadillo>
	NBT_HANDLER = NBTCodec
					.builder(Armadillo.class)
					.include(Animal.NBT_CODEC)
					.intField("scute_time", Armadillo::getScuteTime, Armadillo::setScuteTime, -1)
					.codec("state", Armadillo::getState, Armadillo::setState, EnumUtil.enumStringNBTCodec(ArmadilloState.class), ArmadilloState.IDLE)
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
