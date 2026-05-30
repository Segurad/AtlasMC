package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface TeleportRandomly extends ComponentEffect {
	
	@NotNull
	public static final NBTCodec<TeleportRandomly>
	NBT_CODEC = NBTCodec
					.builder(TeleportRandomly.class)
					.include(ComponentEffect.NBT_CODEC)
					.floatField("diameter", TeleportRandomly::getDiameter, TeleportRandomly::setDiameter, 16)
					.build();
	
	@NotNull
	public static final StreamCodec<TeleportRandomly>
	STREAM_CODEC = StreamCodec
					.builder(TeleportRandomly.class)
					.include(ComponentEffect.STREAM_CODEC)
					.floatValue(TeleportRandomly::getDiameter, TeleportRandomly::setDiameter)
					.build();
	
	float getDiameter();
	
	void setDiameter(float diameter);
	
	@Override
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
