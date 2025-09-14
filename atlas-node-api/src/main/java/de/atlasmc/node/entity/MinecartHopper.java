package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface MinecartHopper extends AbstractMinecartContainer {

	public static final NBTSerializationHandler<MinecartHopper>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MinecartHopper.class)
					.include(AbstractMinecartContainer.NBT_HANDLER)
					.boolField("Enable", MinecartHopper::isEnabled, MinecartHopper::setEnabled)
					.build();
	
	void setEnabled(boolean enabled);
	
	boolean isEnabled();

	@Override
	default NBTSerializationHandler<? extends MinecartHopper> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
