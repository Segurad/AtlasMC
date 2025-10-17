package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface MinecartHopper extends AbstractMinecartContainer {

	public static final NBTCodec<MinecartHopper>
	NBT_HANDLER = NBTCodec
					.builder(MinecartHopper.class)
					.include(AbstractMinecartContainer.NBT_HANDLER)
					.boolField("Enable", MinecartHopper::isEnabled, MinecartHopper::setEnabled)
					.build();
	
	void setEnabled(boolean enabled);
	
	boolean isEnabled();

	@Override
	default NBTCodec<? extends MinecartHopper> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
