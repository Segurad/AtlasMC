package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Mooshroom extends AgeableMob {
	
	public static final NBTCodec<Mooshroom>
	NBT_HANDLER = NBTCodec
					.builder(Mooshroom.class)
					.include(AgeableMob.NBT_CODEC)
					.codec("Type", Mooshroom::getVariant, Mooshroom::setVariant, EnumUtil.enumStringNBTCodec(Variant.class), Variant.RED)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);
	
	public static enum Variant implements EnumName, IDHolder {
		
		RED("red"),
		BROWN("brown");
		
		private String name;
		
		private Variant(String name) {
			this.name = name;
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
