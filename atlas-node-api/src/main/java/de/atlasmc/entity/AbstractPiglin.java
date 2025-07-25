package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractPiglin extends Monster {
	
	public static final NBTSerializationHandler<AbstractPiglin>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractPiglin.class)
					.include(Monster.NBT_HANDLER)
					.boolField("IsImmuneToZombification", AbstractPiglin::isImmune, AbstractPiglin::setImmune, false)
					// int TimeInOverworld
					.build();
	
	boolean isImmune();
	
	void setImmune(boolean immune);
	
	@Override
	default NBTSerializationHandler<? extends AbstractPiglin> getNBTHandler() {
		return NBT_HANDLER;
	}

}
