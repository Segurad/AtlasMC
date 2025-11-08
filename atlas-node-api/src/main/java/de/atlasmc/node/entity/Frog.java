package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Frog extends Animal {

	public static final NBTCodec<Frog>
	NBT_HANDLER = NBTCodec
					.builder(Frog.class)
					.include(Animal.NBT_CODEC)
					.codec("variant", Frog::getVariant, Frog::setVariant, EnumUtil.enumStringNBTCodec(Variant.class), Variant.TEMPERATE)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);
	
	Entity getTongueTarget();
	
	void setTangueTarget(Entity entity);
	
	@Override
	default NBTCodec<? extends Frog> getNBTCodec() {
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
