package de.atlasmc.node.entity;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Mooshroom extends AgeableMob {
	
	public static final NBTSerializationHandler<Mooshroom>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Mooshroom.class)
					.include(AgeableMob.NBT_HANDLER)
					.enumStringField("Type", Mooshroom::getVariant, Mooshroom::setVariant, Variant::getByName, Variant.RED)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);
	
	public static enum Variant implements EnumName, EnumID {
		RED("red"),
		BROWN("brown");
		
		private String name;
		
		private Variant(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static Variant getByID(int id) {
			switch (id) {
			case 0:
				return RED;
			case 1:
				return BROWN;
			default:
				return RED;
			}
		}
		
		public static Variant getByName(String name) {
			if (BROWN.getName().equals(name))
				return BROWN;
			else return RED;
		}
		
	}

}
