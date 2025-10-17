package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface TeleportRandomly extends ComponentEffect {
	
	public static final NBTCodec<TeleportRandomly>
	NBT_HANDLER = NBTCodec
					.builder(TeleportRandomly.class)
					.include(ComponentEffect.NBT_HANDLER)
					.floatField("diameter", TeleportRandomly::getDiameter, TeleportRandomly::setDiameter, 16)
					.build();
	
	float getDiameter();
	
	void setDiameter(float diameter);
	
	TeleportRandomly clone();
	
	@Override
	default NBTCodec<? extends TeleportRandomly> getNBTCodec() {
		return NBT_HANDLER;
	}

}
