package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Frog extends Animal {

	public static final NBTSerializationHandler<Frog>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Frog.class)
					.include(Animal.NBT_HANDLER)
					.enumStringField("variant", Frog::getVariant, Frog::setVariant, Variant.class, Variant.TEMPERATE)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);
	
	Entity getTongueTarget();
	
	void setTangueTarget(Entity entity);
	
	@Override
	default NBTSerializationHandler<? extends Frog> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Variant implements EnumName, IDHolder {
		
		TEMPERATE,
		WARM,
		COLD;
		
		private String name;
		
		private Variant() {
			String name = "minecraft:" + name().toLowerCase();
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
