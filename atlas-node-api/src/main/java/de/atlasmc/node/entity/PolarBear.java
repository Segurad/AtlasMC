package de.atlasmc.node.entity;

import de.atlasmc.node.entity.data.AngerableMob;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PolarBear extends Animal, AngerableMob {
	
	public static final NBTSerializationHandler<PolarBear>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PolarBear.class)
					.include(Animal.NBT_HANDLER)
					.include(AngerableMob.NBT_HANDLER)
					.boolField("StandingUp", PolarBear::isStandingUp, PolarBear::setStandingUp, false) // non standard
					.build();
	
	boolean isStandingUp();
	
	void setStandingUp(boolean standing);

	@Override
	default NBTSerializationHandler<? extends PolarBear> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
