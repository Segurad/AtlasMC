package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AbstractPiglin extends Monster {
	
	public static final NBTCodec<AbstractPiglin>
	NBT_HANDLER = NBTCodec
					.builder(AbstractPiglin.class)
					.include(Monster.NBT_HANDLER)
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
