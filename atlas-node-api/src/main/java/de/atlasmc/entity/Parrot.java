package de.atlasmc.entity;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Parrot extends Tameable {
	
	public static final NBTSerializationHandler<Parrot>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Parrot.class)
					.include(Tameable.NBT_HANDLER)
					.enumIntField("Variant", Parrot::getParrotType, Parrot::setParrotType, Type::getByID, Type.RED_BLUE)
					.build();
	
	Type getParrotType();
	
	void setParrotType(Type type);
	
	@Override
	default NBTSerializationHandler<? extends Parrot> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements EnumID {
		RED_BLUE,
		BLUE,
		GREEN,
		YELLOW_BLUE,
		GREY;
		
		public int getID() {
			return ordinal();
		}
		
		public static Type getByID(int id) {
			switch(id) {
			case 0:
				return RED_BLUE;
			case 1:
				return BLUE;
			case 2:
				return GREEN;
			case 3:
				return YELLOW_BLUE;
			case 4:
				return GREY;
			default:
				return RED_BLUE;
			}
		}
		
	}

}
