package de.atlasmc.sound;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.map.key.CharKey;

public class ResourceSound implements Sound {

	protected static final CharKey
	NBT_SOUND_ID = CharKey.literal("sound_id"),
	NBT_RANGE = CharKey.literal("range");
	
	private final NamespacedKey key;
	private final float fixedRange;
	
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

	@Override
	public float getFixedRange() {
		return fixedRange;
	}

	@Override
	public boolean hasFixedRange() {
		return fixedRange == fixedRange;
	}

}
