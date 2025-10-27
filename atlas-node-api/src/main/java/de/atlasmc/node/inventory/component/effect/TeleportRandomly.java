package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface TeleportRandomly extends ComponentEffect {
	
	public static final NBTCodec<TeleportRandomly>
	NBT_CODEC = NBTCodec
					.builder(TeleportRandomly.class)
					.include(ComponentEffect.NBT_HANDLER)
					.floatField("diameter", TeleportRandomly::getDiameter, TeleportRandomly::setDiameter, 16)
					.build();
	
	public static final StreamCodec<TeleportRandomly>
	STREAM_CODEC = StreamCodec
					.builder(TeleportRandomly.class)
					.include(ComponentEffect.STREAM_CODEC)
					.floatValue(TeleportRandomly::getDiameter, TeleportRandomly::setDiameter)
					.build();
	
	float getDiameter();
	
	void setDiameter(float diameter);
	
	TeleportRandomly clone();
	
	@Override
	default NBTCodec<? extends TeleportRandomly> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends TeleportRandomly> getStreamCodec() {
		return STREAM_CODEC;
	}

}
