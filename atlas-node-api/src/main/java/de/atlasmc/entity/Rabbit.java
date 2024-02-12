package de.atlasmc.entity;

import java.util.List;

public interface Rabbit extends Animal {
	
	public Type getRabbitType();
	
	public void setRabbitType(Type type);
	
	public static enum Type {
		BROWN(0),
		WHITE(1),
		BLACK(2),
		BLACK_AND_WIHTE(3),
		GOLD(4),
		SALT_AND_PEPPER(5),
		THE_KILLER_BUNNY(99);
		
		private static List<Type> VALUES;
		
		private byte id;
		
		private Type(int id) {
			this.id = (byte) id;
		}
		
		public int getID() {
			return id;
		}
		
		public static Type getByID(int id) {
			for (Type type : getValues()) {
				if (type.getID() == id)
					return type;
			}
			return BROWN;
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
