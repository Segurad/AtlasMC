package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface TeleportRandomly extends ComponentEffect {
	
	public static final NBTSerializationHandler<TeleportRandomly>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TeleportRandomly.class)
					.include(ComponentEffect.NBT_HANDLER)
					.floatField("diameter", TeleportRandomly::getDiameter, TeleportRandomly::setDiameter, 16)
					.build();
	
	float getDiameter();
	
	void setDiameter(float diameter);
	
	TeleportRandomly clone();
	
	@Override
	default NBTSerializationHandler<? extends TeleportRandomly> getNBTHandler() {
		return NBT_HANDLER;
	}

}
