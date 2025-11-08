package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.enums.EnumUtil;

public interface Parrot extends Tameable {
	
	public static final NBTCodec<Parrot>
	NBT_HANDLER = NBTCodec
					.builder(Parrot.class)
					.include(Tameable.NBT_HANDLER)
					.codec("Variant", Parrot::getParrotType, Parrot::setParrotType, EnumUtil.enumIntNBTCodec(Type.class), Type.RED_BLUE)
					.build();
	
	Type getParrotType();
	
	void setParrotType(Type type);
	
	@Override
	default NBTCodec<? extends Parrot> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements IDHolder {
		
		RED_BLUE,
		BLUE,
		GREEN,
		YELLOW_BLUE,
		GREY;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
