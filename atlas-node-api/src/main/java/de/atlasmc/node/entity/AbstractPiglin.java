package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface AbstractPiglin extends Monster {
	
	@NotNull
	public static final NBTCodec<AbstractPiglin>
	NBT_HANDLER = NBTCodec
					.builder(AbstractPiglin.class)
					.include(Monster.NBT_CODEC)
					.boolField("IsImmuneToZombification", AbstractPiglin::isImmune, AbstractPiglin::setImmune, false)
					// int TimeInOverworld
					.build();
	
	boolean isImmune();
	
	void setImmune(boolean immune);
	
	@Override
	default NBTCodec<? extends AbstractPiglin> getNBTCodec() {
		return NBT_HANDLER;
	}

}
