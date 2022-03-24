package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTObjectReader;
import de.atlasmc.util.nbt.io.NBTObjectWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * Represents a Object which can be converted to NBT or from NBT
 */
public interface NBTHolder {
	
	/**
	 * 
	 * @return the NBT without system data
	 */
	public default NBT toNBT() {
		return toNBT(false);
	}
	
	/**
	 * 
	 * @param systemData whether or not the system data should
	 * @return NBT 
	 */
	public default NBT toNBT(boolean systemData) {
		NBTObjectWriter writer = new NBTObjectWriter();
		NBT nbt = writer.toNBT();
		try {
			toNBT(writer, systemData);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nbt;
	}
	
	/**
	 * Write the NBT of the Holder
	 * Does not create a new CompoundTag
	 * @param writer
	 * @param systemData true if it is used system internal false while send to client
	 * @throws IOException
	 */
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException;
	
	/**
	 * Reads until a EndTag is reached and goes to the next entry by {@link NBTReader#readNextEntry()}
	 * @param reader
	 * @throws IOException
	 */
	public void fromNBT(NBTReader reader) throws IOException;
	
	public default void fromNBT(NBT nbt) {
		NBTObjectReader reader = new NBTObjectReader(nbt);
		try {
			fromNBT(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
