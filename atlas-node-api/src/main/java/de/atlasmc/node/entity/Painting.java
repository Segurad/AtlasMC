package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Painting extends Hanging {

	public static final NBTCodec<Painting>
	NBT_HANDLER = NBTCodec
					.builder(Painting.class)
					.include(Hanging.NBT_HANDLER)
					.codec("motive", Painting::getMotive, Painting::setMotive, EnumUtil.enumStringNBTCodec(Motive.class), Motive.KEBAB)
					.build();
	
	Motive getMotive();
	
	void setMotive(Motive motive);
	
	@Override
	default NBTCodec<? extends Painting> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static enum Motive implements IDHolder, EnumName {
		
		KEBAB,
		AZTEC,
		ALBAN,
		AZTEC2,
		BOMB,
		PLANT,
		WASTELAND,
		POOL,
		COURBET,
		SEA,
		SUNSET,
		CREEBET,
		WANDERER,
		GRAHAM,
		MATCH,
		BUST,
		STAGE,
		VOID,
		SKULL_AND_ROSES,
		WITHER,
		FIGHTERS,
		POINTER,
		PIGSCENE,
		BURNING_SKULL,
		SKELETON,
		EARTH,
		WIND,
		WATER,
		FIRE,
		DONKEY_KONG;

		private final String name;
		
		private Motive() {
			String name = "minecraft:".concat(name().toLowerCase());
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
