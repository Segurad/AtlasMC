package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTObjectReader;
import de.atlasmc.util.nbt.io.NBTObjectWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

/**
 * Represents a Object which can be converted to NBT or from NBT
 */
public interface NBTHolder {
	
	/**
	 * 
	 * @return the NBT content with systemData = false
	 */
	public default NBT toNBT() {
		return toNBT(false);
	}
	
	/**
	 * 
	 * @param systemData
	 * @return the NBT content with local = default
	 */
	public default NBT toNBT(boolean systemData) {
		NBTObjectWriter writer = new NBTObjectWriter();
		try {
			toNBT(writer, systemData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toNBT();
	}
	
	/**
	 * Write the NBT of the Holder
	 * @param writer
	 * @param local
	 * @param systemData true if it is used system internal false while send to client
	 * @throws IOException
	 * Does not a new CompoundTag
	 */
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException;
	
	/**
	 * 
	 * @param reader
	 * @throws IOException
	 * Reads until a EndTag is reached and reads to the next tag
	 */
	public void fromNBT(NBTReader reader) throws IOException;
	
	public default void fromNBT(NBT nbt) {
		try {
			fromNBT(new NBTObjectReader(nbt));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
