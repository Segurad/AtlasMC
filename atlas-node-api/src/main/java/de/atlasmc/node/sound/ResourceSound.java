package de.atlasmc.node.sound;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public class ResourceSound implements Sound, NBTSerializable, StreamSerializable {

	protected static final CharKey
	NBT_SOUND_ID = CharKey.literal("sound_id"),
	NBT_RANGE = CharKey.literal("range");
	
	public static final NBTCodec<ResourceSound>
	NBT_CODEC = NBTCodec
					.builder(ResourceSound.class)
					.defaultConstructor(ResourceSound::new)
					.namespacedKey("sound_id", ResourceSound::getNamespacedKey, ResourceSound::setNamespacedKey)
					.floatField("range", ResourceSound::getFixedRange, ResourceSound::setFixedRange, 0)
					.build();
	
	public static final StreamCodec<ResourceSound>
	STREAM_CODEC = StreamCodec
					.builder(ResourceSound.class)
					.defaultConstructor(ResourceSound::new)
					.namespacedKey(ResourceSound::getNamespacedKey, ResourceSound::setNamespacedKey)
					.optional(ResourceSound::hasFixedRange)
					.floatValue(ResourceSound::getFixedRange, ResourceSound::setFixedRange)
					.build();
	
	private NamespacedKey key;
	private float fixedRange;
	
	private ResourceSound() {}
	
	public ResourceSound(NamespacedKey key, float fixedRange) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
		this.fixedRange = fixedRange;
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	private void setNamespacedKey(NamespacedKey key) {
		this.key = key;
	}

	@Override
	public float getFixedRange() {
		return fixedRange;
	}
	
	private void setFixedRange(float fixedRange) {
		this.fixedRange = fixedRange;
	}

	@Override
	public boolean hasFixedRange() {
		return fixedRange == fixedRange;
	}
	
	@Override
	public NBTCodec<? extends ResourceSound> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends ResourceSound> getStreamCodec() {
		return STREAM_CODEC;
	}

}
