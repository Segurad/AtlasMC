package de.atlasmc.node.entity;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Salmon extends Fish {
	
	public static final NBTSerializationHandler<Salmon>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Salmon.class)
					.include(Fish.NBT_HANDLER)
					.enumStringField("type", Salmon::getSalmonType, Salmon::setSalmonType, Type.class, Type.MEDIUM)
					.build();

	Type getSalmonType();
	
	void setSalmonType(Type type);
	
	@Override
	default NBTSerializationHandler<? extends Fish> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements EnumName {
		
		SMALL,
		MEDIUM,
		LARGE;

		private final String name;
		
		private Type() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
	
}
