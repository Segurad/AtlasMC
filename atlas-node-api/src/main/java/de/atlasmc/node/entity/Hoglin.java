package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Hoglin extends Animal {
	
	public static final NBTSerializationHandler<Hoglin>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Hoglin.class)
					.include(Animal.NBT_HANDLER)
					.boolField("Cannot", Hoglin::isHuntable, Hoglin::setHuntable, false)
					.boolField("IsImmuneToZombification", Hoglin::isImmune, Hoglin::setImmune, false)
					//.intField("TimeInOverworld", Hoglin::getTimeInOverworld, Hoglin::setTimeInOverworld, 0)
					.build();
	
	boolean isImmune();
	
	void setImmune(boolean immune);

	void setHuntable(boolean huntable);
	
	boolean isHuntable();

	@Override
	default NBTSerializationHandler<? extends Hoglin> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
