package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.node.DyeColor;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Cat extends Tameable {
	
	public static final NBTSerializationHandler<Cat>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Cat.class)
					.include(Tameable.NBT_HANDLER)
					.enumByteField("CollarColor", Cat::getCollarColor, Cat::setCollarColor, DyeColor.class, DyeColor::getID, DyeColor.RED)
					.enumStringField("variant", Cat::getCatType, Cat::setCatType, Type.class, Type.BLACK)
					.build();
	
	Type getCatType();
	
	void setCatType(Type type);
	
	boolean isLying();
	
	void setLying(boolean lying);
	
	boolean isRelaxed();
	
	void setRelaxed(boolean relaxed);
	
	DyeColor getCollarColor();
	
	void setCollarColor(DyeColor color);
	
	@Override
	default NBTSerializationHandler<? extends Cat> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements EnumName, IDHolder {

		TABBY,
		BLACK,
		RED,
		SIAMESE,
		BRITISH_SHORTHAIR,
		CALICO,
		PERSIAN,
		RAGDOLL,
		WHITE,
		JELLIE,
		ALL_BLACK;
		
		private String name;
		
		private Type() {
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
