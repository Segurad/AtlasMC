package de.atlasmc.node;

import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.Nullable;

/**
 * A object that supports a custom name
 */
public interface Nameable {
	
	public static final NBTCodec<Nameable>
	NBT_HANDLER = NBTCodec
					.builder(Nameable.class)
					.codec("CustomName", Nameable::getCustomName, Nameable::setCustomName, Chat.NBT_CODEC)
					.build();
	
	/**
	 * Returns the custom name or null if non is set
	 * @return name or null
	 */
	@Nullable
	Chat getCustomName();
	
	/**
	 * Sets a custom name or null
	 * @param name
	 */
	void setCustomName(Chat name);
	
	/**
	 * Returns whether or not a custom name is set
	 * @return true if set
	 */
	boolean hasCustomName();

}
