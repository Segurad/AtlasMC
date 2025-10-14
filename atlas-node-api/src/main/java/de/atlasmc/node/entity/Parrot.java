package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Parrot extends Tameable {
	
	public static final NBTSerializationHandler<Parrot>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Parrot.class)
					.include(Tameable.NBT_HANDLER)
					.enumIntField("Variant", Parrot::getParrotType, Parrot::setParrotType, Type.class, Type.RED_BLUE)
					.build();
	
	Type getParrotType();
	
	void setParrotType(Type type);
	
	@Override
	default NBTSerializationHandler<? extends Parrot> getNBTHandler() {
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
