package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Hoglin extends Animal {
	
	@NotNull
	public static final NBTCodec<Hoglin>
	NBT_CODEC = NBTCodec
					.builder(Hoglin.class)
					.include(Animal.NBT_CODEC)
					.boolField("Cannot", Hoglin::isHuntable, Hoglin::setHuntable, false)
					.boolField("IsImmuneToZombification", Hoglin::isImmune, Hoglin::setImmune, false)
					//.intField("TimeInOverworld", Hoglin::getTimeInOverworld, Hoglin::setTimeInOverworld, 0)
					.build();
	
	boolean isImmune();
	
	void setImmune(boolean immune);

	void setHuntable(boolean huntable);
	
	boolean isHuntable();

	@Override
	default NBTCodec<? extends Hoglin> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
