package de.atlasmc.node.entity;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Rabbit extends Animal {
	
	public static final NBTSerializationHandler<Rabbit>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Rabbit.class)
					.include(Animal.NBT_HANDLER)
					//.intField("MoreCarrotTicks", null, null)
					.enumIntField("RabbitType", Rabbit::getRabbitType, Rabbit::setRabbitType, Type::getByID, Type.BROWN)
					.build();
	
	Type getRabbitType();
	
	void setRabbitType(Type type);
	
	@Override
	default NBTSerializationHandler<? extends Rabbit> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements EnumID, EnumValueCache {
		BROWN(0),
		WHITE(1),
		BLACK(2),
		BLACK_AND_WIHTE(3),
		GOLD(4),
		SALT_AND_PEPPER(5),
		THE_KILLER_BUNNY(99),
		TOAST(Integer.MAX_VALUE);
		
		private static List<Type> VALUES;
		
		private int id;
		
		private Type(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public static Type getByID(int id) {
			for (Type type : getValues()) {
				if (type.getID() == id)
					return type;
			}
			return TOAST;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Type> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
	}

}
