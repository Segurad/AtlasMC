package de.atlasmc.node.sound;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class ResourceSound implements Sound, NBTSerializable {

	protected static final CharKey
	NBT_SOUND_ID = CharKey.literal("sound_id"),
	NBT_RANGE = CharKey.literal("range");
	
	public static final NBTSerializationHandler<ResourceSound>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ResourceSound.class)
					.defaultConstructor(ResourceSound::new)
					.namespacedKey("sound_id", ResourceSound::getNamespacedKey, ResourceSound::setNamespacedKey)
					.floatField("range", ResourceSound::getFixedRange, ResourceSound::setFixedRange, 0)
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
	public NBTSerializationHandler<? extends ResourceSound> getNBTHandler() {
		return NBT_HANDLER;
	}

}
