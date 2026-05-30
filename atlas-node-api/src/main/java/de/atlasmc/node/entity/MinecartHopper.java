package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface MinecartHopper extends AbstractMinecartContainer {

	@NotNull
	public static final NBTCodec<MinecartHopper>
	NBT_CODEC = NBTCodec
					.builder(MinecartHopper.class)
					.include(AbstractMinecartContainer.NBT_CODEC)
					.boolField("Enable", MinecartHopper::isEnabled, MinecartHopper::setEnabled)
					.build();
	
	void setEnabled(boolean enabled);
	
	boolean isEnabled();

	@Override
	default NBTCodec<? extends MinecartHopper> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
