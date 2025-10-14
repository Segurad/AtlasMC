package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Rabbit extends Animal {
	
	public static final NBTSerializationHandler<Rabbit>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Rabbit.class)
					.include(Animal.NBT_HANDLER)
					//.intField("MoreCarrotTicks", null, null)
					.enumIntField("RabbitType", Rabbit::getRabbitType, Rabbit::setRabbitType, Type.class, Type.BROWN)
					.build();
	
	Type getRabbitType();
	
	void setRabbitType(Type type);
	
	@Override
	default NBTSerializationHandler<? extends Rabbit> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements IDHolder {
		
		BROWN(0),
		WHITE(1),
		BLACK(2),
		BLACK_AND_WIHTE(3),
		GOLD(4),
		SALT_AND_PEPPER(5),
		THE_KILLER_BUNNY(99),
		TOAST(Integer.MAX_VALUE);
		
		private int id;
		
		private Type(int id) {
			this.id = id;
		}
		
		@Override
		public int getID() {
			return id;
		}
		
	}

}
