package de.atlasmc.node.sound;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public interface Sound extends Namespaced {

	public static final NBTCodec<Sound> NBT_CODEC = NBTCodec.codecOrElse(Sound.class, EnumSound.NBT_CODEC, ResourceSound.NBT_CODEC);
	
	public static final StreamCodec<Sound> STREAM_CODEC = StreamCodec.varIntEnumOrCodec(Sound.class, EnumSound.class, ResourceSound.STREAM_CODEC);
	
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
	
}
