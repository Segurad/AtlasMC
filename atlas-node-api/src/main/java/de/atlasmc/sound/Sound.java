package de.atlasmc.sound;

import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.serialization.fields.NBTField;

public interface Sound extends Namespaced {

	final long DEFAULT_SEED = 0x4aa2903b89ec2ff6L;

	/**
	 * Returns the fixed range value if the value is NaN no fixed range is set.
	 * @return fixed range
	 */
	float getFixedRange();
	
	/**
	 * Returns whether or not a fixed range is set.
	 * @return true if fixed range
	 * @implNote {@link #getFixedRange()} != {@link #getFixedRange()}
	 */
	boolean hasFixedRange();
	
	public static <T> NBTField<T> getNBTSoundField(CharSequence key, Function<T, Sound> get, BiConsumer<T, Sound> set, Sound defaultSound) {
		return new SoundField<>(key, get, set, defaultSound);
	}
	
}
