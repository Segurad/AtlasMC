package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Salmon extends Fish {
	
	@NotNull
	public static final NBTCodec<Salmon>
	NBT_CODEC = NBTCodec
					.builder(Salmon.class)
					.include(Fish.NBT_CODEC)
					.codec("type", Salmon::getSalmonType, Salmon::setSalmonType, EnumUtil.enumStringNBTCodec(Type.class), Type.MEDIUM)
					.build();

	Type getSalmonType();
	
	void setSalmonType(Type type);
	
	@Override
	default NBTCodec<? extends Fish> getNBTCodec() {
		return NBT_CODEC;
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
